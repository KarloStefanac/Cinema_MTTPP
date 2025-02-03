# Testiranje aplikacije za rezervaciju sjedala u kinodvorani

Ovaj dokument opisuje proces testiranja web aplikacije koja se koristi za biranje filma i rezervaciju sjedala u kinodvorani. Dokument navodi nekoliko primjera testova koji su se koristili kako bi se pokrile funkcionalnosti ove web aplikacije.

## Struktura testnog direktorija
Direktoriji u kojima se nalaze testovi su sljedeći:

```
Cinema_MTTP/
|-- src/
    |-- main/
    |-- test/
        |-- controller/
            |-- MovieControllerTest/
        |-- service/
            |-- MovieServiceUT/
            |-- MovieServiceIT/
```
- **`controller/`**: Sadrži REST API testove za kontrolere.
- **`service/`**: Sadrži Unit i Integration testove za servise.


## Unit testovi
Unit testovi testiraju komponente servisa izolirano od drugih komponenti. Cilj je potvrditi ispravnost rada te komponente.

Primjer Unit Testa za dohvaćanje spremljenih filmova:
```java
    @Test
    void testGetAllMovies(){
        List<MovieEntity> movieEntities = List.of(new MovieEntity(), new MovieEntity());
        List<MovieDto> movieDtos = List.of(new MovieDto(), new MovieDto());

        when(movieRepository.findAll()).thenReturn(movieEntities);
        when(movieMapper.mapToMovieDto(any(MovieEntity.class))).thenReturn(new MovieDto());

        List<MovieDto> result = movieService.getAllMovies();

        assertEquals(2, result.size());
        verify(movieRepository, times(1)).findAll();
        verify(movieMapper, times(2)).mapToMovieDto(any(MovieEntity.class));
    }
```
Pošto Unit testovi ne smiju komunicirati sa repozitorijom, funkcije koje bi dohvaćale podatke moramo simulirati (Mockati) pomoću `Mockito` okruženja.

`movieService.getAllMovies()` je pravi poziv funkcije iz servisa koju želimo testirati. Unutar te funkcije se koristi `movieRepository.findAll()` funkcija koja dohvaća podatke iz repozitorija koju moramo simulirati. Nakon dohvaćanja podaci se pretvaraju iz Entity oblika u DTO oblik koji se koristi za prikaz na frontendu, što također moramo simulirati

`when(movieRepository.findAll()).thenReturn(movieEntities);` definira što bi funkcija koja dohvaća podatke iz repozitorija trebala vratiti prilikom poziva `movieRepository.findAll()` unutar servisa.

`when(movieMapper.mapToMovieDto(any(MovieEntity.class))).thenReturn(new MovieDto());` simulira pretvorbu iz MovieEntity tipa (dohvaćen iz baze) u MovieDto tip (spreman za prikaz). Razlog mocaknja ove funkcije je taj što testiramo dohvaćanje filmova u izolaciji, ne želimo da nam potencijalna greška druge komponente utječe na Unit test ove.


## Integracijski testovi
Integracijski testovi se pokreću nad testnom bazom podataka kako nebi utjecali na glavnu bazu.
Cilj je provjeriti spremaju li se podaci ispravno u bazu, te dohvaćaju li se ispravno.

Primjer:
```java
    @Test
    void testAddMovie(){
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setName("Inception");
        movieRequest.setDirector("Christopher Nolan");
        movieRequest.setDuration(148);
        movieRequest.setGenres(List.of(Genre.Thriller));
        movieRequest.setPrice(15);
        movieRequest.setImage("InceptionImage");

        movieService.addMovie(movieRequest);

        List<MovieDto> movies = movieService.getAllMovies();
        assertEquals(1, movies.size());
        assertEquals("Inception", movies.get(0).getName());
        assertEquals("Christopher Nolan", movies.get(0).getDirector());
        assertEquals(148, movies.get(0).getDuration());
        assertEquals(List.of(Genre.Thriller), movies.get(0).getGenres());
        assertEquals(15, movies.get(0).getPrice());
        assertEquals("InceptionImage", movies.get(0).getImage());
    }
```
U testu `testAddMovie()` stvaramo novi objekt filma koji popunjavamo podacima. Taj film spremamo u testnu bazu pomoću `movieService.addMovie(movieRequest)`. Prije dodavanja ovog filma baza je prazna, stoga možemo provjeriti je li film ispravno dodan dohvaćanjem svih filmova i provjeravanjem vrijednosti za taj film.

Baza se prazni prilikom svakog pokretanja testa te se koristi `@BeforeEach` anotacija kako bi se testna baza očistila prije svakog testa.
```
    @BeforeEach
    void setUp() {
        // Clean up the database before each test
        movieRepository.deleteAll();
    }
```
## REST API testovi
Za testiranje REST API-ja koristi se Spring dodatak `MockMvc` koji simulira HTTP zahtjeve i odgovore za testiranje bez potrebe za pokretanjem web aplikacije.
Kako bi mogli simulirati slanje podataka na frontend, potrebno je složiti `setUp()` metodu sa `@BeforeEach` anotacijom u kojoj pripremamo podatke (objekte za filmove, dvorane i termine) za slanje.

Primjer REST API testa:

```java
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testShowMovieScreenings() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/movie/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("movieScreenings"))
                .andExpect(model().attributeExists("screenings"))
                .andExpect(model().attributeExists("id"));
    }
```
Ovaj test simulira prikaz svih termina za odabrani film. `MockMvcResultBuilders.get("/movie/1")` šalje zahtjev na putanju "movie/1", odnosno entitet filma sa id=1. Odlaskom na taj endpoint očekujemo učitavanje view-a (stranice, odnosno HTML datoteke) `movieScreenings`, očekujemo da će se vratiti 200 OK status, te očekujemo postojanje atributa `screenings` i `id`. `Screenings` sadržava termine koji postoje za taj film i oni se prikazuju na frontendu. `Id` označava film za koji dohvaćamo termine.

Ako pogledamo implementaciju za `ShowMovieScreenings()` možemo vidjeti elemente koje pokriva navedeni test (unutar parametra `model` spremamo objekte za `screenings`):

```java
    @GetMapping("/movie/{id}")
    public String showMovieScreenings(@PathVariable Long id, Model model){
        List<ScreeningDto> screenings = movieService.getMovieScreenings(id);

        model.addAttribute("screenings", screenings);
        model.addAttribute("id", id);
        return "movieScreenings";
    }
```

Sljedeći primjer testa simulira upisivanje podataka u formu za dodavanje novog filma.

```java
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSaveMovie() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/movie/save")
                        .param("name", "Inception")
                        .param("director", "Christopher Nolan")
                        .param("duration", "148")
                        .param("genres", "Action")
                        .param("price", "20")
                        .param("image", "inception.jpg")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(redirectedUrl("/movie?success"));
    }
```
Važna stvar za napomenuti je `@WithMockUser(username = "admin", roles = {"ADMIN"})` anotacija koja simulira autentifikaciju `ADMIN` korisnika, iz razloga što implementacija dopušta dodavanje filmova samo korisnicima sa `ADMIN` privilegijama.

`.param("name", "Inception")` prikazuje jedan od atributa koji se primaju na frontendu prilikom upisa u formu. Prvi parametar označuje ime atributa, a drugi parametar podatak koji želimo "upisati". Na kraju je potrebno simulirati `csrf()` token kako bi uspješno mogli poslati podatke forme na backend. Nakon submittanja podataka iz forme očekujemo redirekciju na `/movie?success` putanju.