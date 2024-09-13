package kmp.player.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hsl_player.dataClass.HslModel
import kmp.player.ui.home.viewModel.HomeViewModel
import kmp.player.ui.player.PlayerScreen
import org.jetbrains.compose.resources.painterResource
import playermultiplatform.composeapp.generated.resources.Res
import playermultiplatform.composeapp.generated.resources.baseline_cast_24

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = viewModel { HomeViewModel() }

        ViewContainer(viewModel)
    }
}

@Composable
fun ViewContainer(viewModel: HomeViewModel){
    val hslDetails: List<HslModel> by viewModel.hslDetails.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ){
            Text(text = "Player_Multiplatform", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(top = 25.dp), fontSize = 25.sp, fontStyle = FontStyle.Italic)

            RecyclerView(hslDetails)
        }
    }
}

@Composable
fun RecyclerView(hslDetails: List<HslModel>) {
    LazyColumn {
        items(items = hslDetails) {
            UserCard(it)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserCard(hslDetail: HslModel) {
    val navigator = LocalNavigator.currentOrThrow

    Card (
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp).fillMaxWidth(),
        shape = RoundedCornerShape(CornerSize(10.dp)),
        elevation = 8.dp,
        onClick = {
            navigator.push(PlayerScreen(hslDetail))
        }
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            Image(
                painter = painterResource(Res.drawable.baseline_cast_24),
                contentDescription = "image",
                modifier = Modifier
                    .padding(8.dp)
                    .size(60.dp)
                    .clip(RoundedCornerShape(CornerSize(6.dp)))
                    .align(alignment = Alignment.CenterVertically)
            )

            Text(text = hslDetail.name, modifier = Modifier.padding(10.dp, 20.dp))
        }
    }
}