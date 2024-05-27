package com.incpeticode.dogbreedwitharchitecture.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.incpeticode.dogbreedwitharchitecture.data.util.Constants
import com.incpeticode.dogbreedwitharchitecture.ui.presenter.DogPresenter
import com.incpeticode.dogbreedwitharchitecture.ui.presenter.DogView
import com.incpeticode.dogbreedwitharchitecture.ui.theme.DogBreedWithArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.CustomInjection.inject
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(),DogView {

    @Inject
    lateinit var presenter: DogPresenter
    private val breedList = mutableStateOf<Map<String, List<String>>?>(null)
    private val randomImage = mutableStateOf(Constants.dogImageModelSample.imageUrl)
    private val isLoading = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        presenter.attachView(this)
        setContent {
            DogBreedWithArchitectureTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { RandomImageTopBar(randomImage.value) },
                    content = { innerPadding ->
                        DogBreedScreen(
                            breedList.value,
                            isLoading.value,
                            { presenter.fetchRandomImageByBreed(it) },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                )
            }
        }
        presenter.fetchAllBreeds()  // Call fetchAllBreeds only once here
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showBreeds(breeds: Map<String, List<String>>) {
        breedList.value = breeds
    }

    override fun showRandomImage(imageUrl: String) {
        randomImage.value = imageUrl
    }

    override fun setLoading(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }
}