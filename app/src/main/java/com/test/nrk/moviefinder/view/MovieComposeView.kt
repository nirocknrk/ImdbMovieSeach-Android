package com.test.nrk.moviefinder.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.test.nrk.moviefinder.model.Movie


@Composable
fun MovieScreen(movies:List<Movie>?,
                isSearching:Boolean,
                errorMessage:String?,
                onSearch: (String) -> Unit) {

    Column {
        SearchBarView { query ->
            onSearch(query)
        }

        when {
            isSearching -> IndeterminateCircularIndicator()
            errorMessage.isNullOrEmpty().not() -> Text(errorMessage!!, Modifier.padding(16.dp))
            movies.isNullOrEmpty() -> Text("No results found", Modifier.padding(16.dp))
            else -> LazyColumn {
                items(movies) { movie ->
                    MovieItem(movie)
                }
            }
        }
    }
}

@Composable
fun SearchBarView(onSearch: (String) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }

    TextField(
        value = query,
        onValueChange = {
            query = it
            onSearch(it)
        },
        singleLine = true,
        placeholder = { Text("Search movies...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(120.dp)
            )
            Column(Modifier.padding(8.dp).weight(1f)) {
                Text(text = movie.title, fontWeight = FontWeight.Bold)
                Text(text = "Year: ${movie.year}")
                Text(text = "Type: ${movie.type}")
            }
        }
    }
}

@Composable
fun IndeterminateCircularIndicator() {

    Text("Searching...", Modifier.padding(16.dp))


    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .padding(16.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }

}


@Preview
@Composable
fun PreviewMovieScreen2() {
    val list = listOf(
        Movie(id = "1",
            title = "The Apple Dumpling Gang",
            year = "1975",
            type= "movie",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BNTU3MzNjZTctYzE3ZC00Y2I1LWFlYTgtYWY2ZTNhYTE4NTJmXkEyXkFqcGc@._V1_SX300.jpg"),
        Movie(
            id = "2",
            title = "Inception2",
            year = "2010",
            type= "movie",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BYWY3NmRmMmItNDllNS00MTQ0LTk3ZDAtMjIxMzkzZjAzYmM4XkEyXkFqcGc@._V1_SX300.jpg"),
    )
    MovieScreen(list,false,""){}
}