package com.example.tradesykmmandroidtrial

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.tradesykmmrd.*

class MainActivity : DefaultAppActivity() {
    private lateinit var homePageResponse: MutableState<HomePageResponse>
    private lateinit var scaffoldState: ScaffoldState
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var clickCount: MutableState<Int>
    private lateinit var animatedProgress: Animatable<Float, AnimationVector1D>
    private lateinit var expanded: MutableState<Boolean>
    private lateinit var liked: MutableState<Boolean>
    private lateinit var result: MutableState<String>

    private val openDialog = mutableStateOf(false)

    @Composable
    override fun MyApp(savedInstanceState: Bundle?) {
        val shouldShowPromotions = rememberSaveable { mutableStateOf(false) }
        val shouldShowError = rememberSaveable { mutableStateOf(false) }

        homePageResponse = remember { mutableStateOf(HomePageResponse()) }

        if (shouldShowPromotions.value) {
            ShowPromotions()
        } else if (shouldShowError.value) {
            ShowError(onRetry = { shouldShowError.value = false })
        } else {
            ShowLoading(
                onSuccess = { shouldShowPromotions.value = true },
                onError = { shouldShowError.value = true }
            )
        }
    }

    @Composable
    private fun ShowPromotions() {
        Card(
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier
        ) {
            // Consider negative values to mean 'cut corner' and positive values to mean 'round corner'
//            val sharpEdgePercent = -50f
//            val roundEdgePercent = 45f
//            // Start with sharp edges
//            val animatedProgress = rememberSaveable { Animatable(sharpEdgePercent) }
//            // Create a coroutineScope for the animation
//            coroutineScope = rememberCoroutineScope()
//            // animation value to animate shape
//            val progress = animatedProgress.value.roundToInt()
//
//            // When progress is 0, there is no modification to the edges so we are just drawing a rectangle.
//            // This allows for a smooth transition between cut corners and round corners.
//            val fabShape = when {
//                progress < 0 -> {
//                    CutCornerShape(abs(progress))
//                }
//                progress == roundEdgePercent.toInt() -> {
//                    CircleShape
//                }
//                else -> {
//                    RoundedCornerShape(progress)
//                }
//            }
            // lambda to call to trigger shape animation
//            val changeShape: () -> Unit = {
//                val target = animatedProgress.targetValue
//                val nextTarget = if (target == roundEdgePercent) sharpEdgePercent else roundEdgePercent
//                coroutineScope.launch {
//                    animatedProgress.animateTo(
//                        targetValue = nextTarget,
//                        animationSpec = TweenSpec(durationMillis = 600)
//                    )
//                }
//            }
//
            expanded = remember { mutableStateOf(false) }
//            liked = remember { mutableStateOf(true) }
//            result = remember { mutableStateOf("") }
            scaffoldState = rememberScaffoldState()
            coroutineScope = rememberCoroutineScope()
//            clickCount = remember { mutableStateOf(0) }

            Scaffold(
                scaffoldState = scaffoldState,
                snackbarHost = {
                    // reuse default SnackbarHost to have default animation and timing handling
                    SnackbarHost(it) { data ->
                        // custom snackbar with the custom border
                        Snackbar(
                            modifier = Modifier,
                            snackbarData = data
                        )
                    }
                },
                topBar = {
                    TopAppBar(
                        title = { Text("My Title") },
                        navigationIcon = {
                            // navigation icon is use
                            // for drawer icon.
                            IconButton(onClick = {
                                coroutineScope.launch { scaffoldState.drawerState.open() }
                            }) {
                                Icon(Icons.Filled.Menu, "menu")
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                result.value = " Play icon clicked"
                            }) {
                                Icon(Icons.Filled.PlayCircle, contentDescription = "")
                            }

                            IconToggleButton(
                                checked = false,
//                                checked = liked.value,
                                onCheckedChange = {
                                    liked.value = it
//                                    if (liked.value) {
//                                        result.value = "Liked"
//                                    } else {
//                                        result.value = "Unliked"
//                                    }
//                                    appThemeState.value = appThemeState.value.copy(isDarkTheme = it)
                                }
                            ) {
                                val tint by animateColorAsState(
                                    Color(0xFF7BB661)
//                                    if (liked.value) Color(0xFF7BB661)
//                                    else Color.LightGray
                                )
                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription = "Localized description",
                                    tint = tint
                                )
                            }

                            Box(
                                Modifier
                                    .wrapContentSize(Alignment.TopEnd)
                            ) {
                                IconButton(onClick = {
                                    expanded.value = true
//                                    result.value = "More icon clicked"
                                }) {
                                    Icon(
                                        Icons.Filled.MoreVert,
                                        contentDescription = "Localized description"
                                    )
                                }

                                DropdownMenu(
                                    expanded = expanded.value,
                                    onDismissRequest = { expanded.value = false },
                                ) {
//                                    LoadColorPaletteDropdownMenuItems()
                                }
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar(/*cutoutShape = fabShape*/) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch { scaffoldState.drawerState.open() }
                            }
                        ) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                },
                drawerContent = {
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                coroutineScope.launch { scaffoldState.drawerState.close() }
                                showToastExample()
                            },
                        text = "Drawer Option 1"
                    )
                    Divider()
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                coroutineScope.launch { scaffoldState.drawerState.close() }
                                showToastExample()
                            },
                        text = "Drawer Option 2"
                    )
                    Divider()
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                coroutineScope.launch { scaffoldState.drawerState.close() }
                                showToastExample()
                            },
                        text = "Drawer Option 3"
                    )
                    Divider()
                    // Drawer items
                },
                floatingActionButton = {
                    BuildFloatingActionButton()
                },
                floatingActionButtonPosition = FabPosition.Center,
                drawerGesturesEnabled = true,
                isFloatingActionButtonDocked = true,
            ) {
                homePageResponse.value.homepage?.homePageData?.toList()?.let {
                    ScreenContent(it)
                }
            }
        }
    }

    @Composable
    private fun ScreenContent(items: List<Pair<String, HomePagePromotion>>) {
//        val isDarkTheme = appThemeState.component1().isDarkTheme
//        val colorPalette = appThemeState.component1().colorPalette
//        val colors = getAppThemeColors(isDarkTheme, colorPalette)
//        val statusBarColor = colors.background

        LazyColumn {
            items(items = items) { homePageData ->
                val key = homePageData.first
                val value = homePageData.second
                val header = value.header
                val subTitle = value.subtitle

                Column(
                    modifier = Modifier
//                        .background(
//                            color = statusBarColor,
//                            shape = RoundedCornerShape(4.dp)
//                        )
                        .padding(8.dp)
                        .semantics { contentDescription = homePageData.first }
                        .clickable {
                            showToastExample()
                        }
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val painter = rememberImagePainter(data = value.imageMobile)

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(value.image)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.ic_baseline_image_24),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            // Set image size to 40 dp
                            // Clip image to be shaped as a circle
                            .clipToBounds()
                            .border(1.5.dp, MaterialTheme.colors.secondary),
                        //Use painter in Image composable
//                        painter = painter,
                        contentDescription = value.trend,
                        error = painterResource(R.drawable.ic_baseline_error_24)
                    )
                    header?.let { Text(text = it) }
                    subTitle?.let { Text(text = it) }
                    Divider(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }

    private fun showToastExample() {
        Toast.makeText(this, "Toast Example...", Toast.LENGTH_SHORT).show()
    }

    @Composable
    fun BuildFloatingActionButton() {
        ExtendedFloatingActionButton(
            modifier = Modifier,
            elevation = FloatingActionButtonDefaults.elevation(),
            onClick = {
                coroutineScope.launch {
                    val result = scaffoldState.snackbarHostState
                        .showSnackbar(
                            message = "Snackbar # ${clickCount.value}",
                            actionLabel = "Action",
                            // Defaults to SnackbarDuration.Short
                            duration = SnackbarDuration.Indefinite
                        ).also { clickCount.value = clickCount.value + 1 }

                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            /* Handle snackbar action performed */
                        }
                        SnackbarResult.Dismissed -> {
                            /* Handle snackbar dismissed */
                        }
                    }
                }
            },
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorite"
                )
            },
            text = { Text("Like") }
        )
    }

    @Composable
    private fun ShowError(onRetry: () -> Unit) {
        Card(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .fillMaxSize(),
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Surface {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.generic_error_text)
                    )
                    Button(
                        modifier = Modifier.padding(vertical = 24.dp),
                        onClick = { onRetry() }
                    ) {
                        Text(stringResource(R.string.retry_text))
                    }
                }
            }
        }
    }

    @Composable
    private fun ShowLoading(onSuccess: () -> Unit, onError: () -> Unit) {
        Card(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .fillMaxSize(),
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Surface {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CreateCircularProgressIndicator(
                        progress = 0.0f,
                        modifier = Modifier.padding(8.dp),
                        color = Color.Green,
                        strokeWidth = 2.dp
                    )
                }
            }
        }
        LaunchedEffect(Unit) {
            homePageResponse.value = ApiClient().tradesyHomePage()
            println("response=[${homePageResponse.value}]")
            onSuccess()
        }
    }

    @Composable
    private fun CreateCircularProgressIndicator(
        modifier: Modifier = Modifier,
        progress: Float = 0.0f,
        color: Color = MaterialTheme.colors.primary,
        strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth
    ) {
        val infiniteTransition = rememberInfiniteTransition()
        val progressAnimationValue by infiniteTransition.animateFloat(
            initialValue = progress,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(animation = tween(900))
        )

        CircularProgressIndicator(
            modifier = modifier,
            progress = progressAnimationValue,
            color = color,
            strokeWidth = strokeWidth
        )
    }

    @Preview(
        fontScale = 1.5f,
        name = "In Progress Light Mode",
        uiMode = Configuration.UI_MODE_NIGHT_NO,
        showSystemUi = true,
        showBackground = true,
        widthDp = 320,
        heightDp = 320
    )
    @Composable
    fun LoadingLightPreview() {
        KtorTutorial101Theme {
            ShowLoading(
                onSuccess = { },
                onError = { }
            )
        }
    }

    @Preview(
        fontScale = 1.5f,
        name = "In Progress Dark Mode",
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showSystemUi = true,
        showBackground = true,
        widthDp = 320,
        heightDp = 320
    )
    @Composable
    fun LoadingDarkModePreview() {
        KtorTutorial101Theme {
            ShowLoading(
                onSuccess = { },
                onError = { }
            )
        }
    }

    @Preview(
        fontScale = 1.5f,
        name = "On Error Light Mode",
        uiMode = Configuration.UI_MODE_NIGHT_NO,
        showSystemUi = true,
        showBackground = true,
        widthDp = 320,
        heightDp = 320
    )
    @Composable
    fun ErrorLightPreview() {
        KtorTutorial101Theme {
            ShowError(onRetry = { })
        }
    }

    @Preview(
        fontScale = 1.5f,
        name = "On Error Dark Mode",
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showSystemUi = true,
        showBackground = true,
        widthDp = 320,
        heightDp = 320
    )
    @Composable
    fun ErrorDarkModePreview() {
        KtorTutorial101Theme {
            ShowError(onRetry = { })
        }
    }

    @Preview(
        fontScale = 1.5f,
        name = "On Error Light Mode",
        uiMode = Configuration.UI_MODE_NIGHT_NO,
        showSystemUi = true,
        showBackground = true,
        widthDp = 320,
        heightDp = 320
    )
    @Composable
    fun ScreenContentLightPreview() {
        KtorTutorial101Theme {
            homePageResponse = remember { mutableStateOf(HomePageResponse()) }
            ShowPromotions()
        }
    }

    @Preview(
        fontScale = 1.5f,
        name = "On Screen Content Dark Mode",
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showSystemUi = true,
        showBackground = true,
        widthDp = 320,
        heightDp = 320
    )
    @Composable
    fun ScreenContentDarkModePreview() {
        KtorTutorial101Theme {
            homePageResponse = remember { mutableStateOf(HomePageResponse()) }
            ShowPromotions()
        }
    }
//        tv.text = greet()
//
}
