package com.test.nrk.moviefinder.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.test.nrk.moviefinder.model.Movie
import com.test.nrk.moviefinder.repository.MovieRepository
import com.test.nrk.moviefinder.viewmodel.MovieViewModel


//@Composable
//fun SearchBar(onSearch: (String) -> Unit) {
//    var query by remember { mutableStateOf("") }
//
//    TextField(
//        value = query,
//        onValueChange = {
//            query = it
//            if (it.length > 2) onSearch(it)
//        },
//        placeholder = { Text("Search movies...") },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    )
//}

@Composable
fun MovieScreen(viewModel: MovieViewModel = hiltViewModel()) {
    val movies by viewModel.moviesList.collectAsState()

    Column {
//        SearchBar { query -> viewModel.search(query) }

        when {
            movies == null -> Text("Start searching!", Modifier.padding(16.dp))
            movies!!.isEmpty() -> Text("No results found", Modifier.padding(16.dp))
            else -> LazyColumn {
//                items(movies!!) { movie ->
//                    MovieItem(movie)
//                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                modifier = Modifier.size(100.dp)
            )
            Column(Modifier.padding(8.dp)) {
                Text(text = movie.title, fontWeight = FontWeight.Bold)
                Text(text = "Year: ${movie.year}")
            }
        }
    }
}

@Preview
@Composable
fun PreviewMovieItemScreen() {
    MovieItem(Movie(id = "1", title = "Inception", year = "2010",type= "movie", posterUrl = "https://via.placeholder.com/100"))
}

//@Preview
//@Composable
//fun PreviewMovieScreen() {
//    val mockMovies = listOf(
//        Movie(id = "1", title = "Inception", year = "2010",type= "movie", posterUrl = "https://via.placeholder.com/100"),
//        Movie(id = "2", title = "Interstellar", year = "2014", type= "movie",posterUrl = "https://via.placeholder.com/100"),
//        Movie(id = "3", title = "The Dark Knight", year = "2008", type= "movie",posterUrl = "https://via.placeholder.com/100")
//    )
//
//    val fakeViewModel = object : MovieViewModel(FakeMovieRepository()) {
//        init {
//            _movies.value = mockMovies
//        }
//    }
//
//    MovieScreen(viewModel = fakeViewModel)
//}

//class FakeMovieRepository : MovieRepository {
//    override suspend fun searchMovies(query: String): List<Movie>? {
//        return listOf(
//            Movie(id = "1",title = "Preview Movie 1", year = "2023", type= "movie", posterUrl = "https://via.placeholder.com/100"),
//            Movie(id = "2",title = "Preview Movie 2", year = "2022", type= "movie", posterUrl = "https://via.placeholder.com/100")
//        )
//    }
//}