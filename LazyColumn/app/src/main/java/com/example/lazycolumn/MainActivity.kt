package com.example.lazycolumn

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.lazycolumn.ui.theme.LazyLayoutsTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyLayoutsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListaSimples(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Produto(val id: Int, val nome: String, val preco: Double, val imagem: String)

data class ProdutoState(val produtos: List<Produto> = emptyList())

class ProdutoViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProdutoState())
    val state: StateFlow<ProdutoState> = _state

    init {
        gerarProdutos()
    }

    private fun gerarProdutos() {
        val listaProdutos = List(60) {
            Produto(
                id = it,
                nome = "Produto $it",
                preco = (10..100).random().toDouble(),
                imagem = "https://picsum.photos/200?random=$it"
            )
        }
        _state.value = ProdutoState(produtos = listaProdutos)
    }
}

@Composable
fun ProdutoCard(produto: Produto, onCLick: (Produto) -> Unit) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = { onCLick(produto) }
    ) {
        Row() {
            AsyncImage(
                model = produto.imagem,
                contentDescription = produto.nome,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(60.dp))
            )
            Column() {
                Text(
                    "Nome: ${produto.nome}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
                Text(
                    "Valor: ${produto.preco}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }
        }

    }
}

@Composable
fun ListaSimples(modifier: Modifier = Modifier, viewModel: ProdutoViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    val contexto = LocalContext.current

    Column(modifier = modifier.fillMaxSize()) {

        // LazyColumn
        Text(
            text = "Lista Vertical",
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(state.produtos, key = { it.id }) { produto ->
                ProdutoCard(produto) {
                    Toast.makeText(
                        contexto,
                        "Column: ${produto.nome}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // LazyRow
        Text(
            text = "Lista Horizontal",
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            items(state.produtos, key = { it.id }) { produto ->
                ProdutoCard(produto) {
                    Toast.makeText(
                        contexto,
                        "Row: ${produto.nome}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // LazyVerticalGrid
        Text(
            text = "Grid de Produtos",
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(state.produtos.size) { index ->
                val produto = state.produtos[index]

                ProdutoCard(produto) {
                    Toast.makeText(
                        contexto,
                        "Grid: ${produto.nome}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(60) { index -> Text("Item $index") }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LazyLayoutsTheme {
        Greeting("Android")
    }
}