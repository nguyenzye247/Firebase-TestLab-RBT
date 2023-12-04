package nguyen.hl.firebasetestlabrbt

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nguyen.hl.firebasetestlabrbt.ui.theme.FirebaseTestlabRBTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseTestlabRBTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestArea(
                        onListItemClicked = {
                            Toast.makeText(
                                this@MainActivity,
                                "List Item Clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onFabClicked = {
                            Toast.makeText(
                                this@MainActivity,
                                "Fab Clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onArrowKeyClicked = {
                            Toast.makeText(
                                this@MainActivity,
                                "${it.name} Clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestArea(
    onListItemClicked: () -> Unit = {},
    onFabClicked: () -> Unit = {},
    onArrowKeyClicked: (ArrowKey) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Test App",
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onFabClicked.invoke()
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        TestContent(
            innerPadding,
            onItemClicked = {
                onListItemClicked.invoke()
            },
            onArrowKeyClicked = {
                onArrowKeyClicked.invoke(it)
            }
        )
    }
}

@Composable
fun TestContent(
    innerPadding: PaddingValues,
    onItemClicked: () -> Unit,
    onArrowKeyClicked: (ArrowKey) -> Unit
) {
    var textFieldValue by rememberSaveable {
        mutableStateOf("")
    }
    var arrowText by rememberSaveable {
        mutableStateOf("Press the arrow keys")
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            items(10) {
                ListItem(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                    itemPos = it,
                    onItemClicked = {
                        onItemClicked.invoke()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
            },
            modifier = Modifier
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp),
            trailingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            placeholder = {
                Text(text = "Enter here", style = MaterialTheme.typography.bodySmall)
            }
        )

        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        Box(
            modifier = Modifier
                .size(width = 300.dp, height = 200.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "This is an ad",
                style = MaterialTheme.typography.displayMedium,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 32.dp))

        Text(
            text = arrowText,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FilledIconButton(
                onClick = {
                    arrowText = ArrowKey.UP.name
                    onArrowKeyClicked.invoke(ArrowKey.UP)
                },
                modifier = Modifier
                    .size(56.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Up")
            }

            Spacer(modifier = Modifier.size(4.dp))

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    onClick = {
                        arrowText = ArrowKey.LEFT.name
                        onArrowKeyClicked.invoke(ArrowKey.LEFT)
                    },
                    modifier = Modifier
                        .size(56.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Left")
                }

                Spacer(modifier = Modifier.size(4.dp))

                FilledIconButton(
                    onClick = {
                        arrowText = ArrowKey.DOWNN.name
                        onArrowKeyClicked.invoke(ArrowKey.DOWNN)
                    },
                    modifier = Modifier
                        .size(56.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Down")
                }

                Spacer(modifier = Modifier.size(4.dp))

                FilledIconButton(
                    onClick = {
                        arrowText = ArrowKey.RIGHT.name
                        onArrowKeyClicked.invoke(ArrowKey.RIGHT)
                    },
                    modifier = Modifier
                        .size(56.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Right")
                }
            }
        }
    }
}

@Composable
fun ListItem(modifier: Modifier = Modifier, itemPos: Int, onItemClicked: () -> Unit) {
    Column(
        modifier = modifier
            .clickable {
                onItemClicked.invoke()
            }
            .background(
                MaterialTheme.colorScheme.secondary,
                shape = AbsoluteRoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier,
            text = "Item $itemPos",
            color = Color.White
        )
        Text(
            modifier = Modifier,
            text = "Click here",
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FirebaseTestlabRBTTheme {
        TestArea()
    }
}

enum class ArrowKey {
    UP, DOWNN, LEFT, RIGHT
}
