package ejercicios.dam.intermodulardam.publication

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import ejercicios.dam.intermodulardam.comments.domain.entity.Comment
import ejercicios.dam.intermodulardam.comments.ui.CommentViewModel
import ejercicios.dam.intermodulardam.login.ui.PlaceholderForField
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.main.ui.PostPhoto
import ejercicios.dam.intermodulardam.main.ui.PublicationViewModel
import ejercicios.dam.intermodulardam.main.ui.WaitingScreen
import ejercicios.dam.intermodulardam.ui.composable.MainBottomBar
import ejercicios.dam.intermodulardam.ui.composable.MainDrawer
import ejercicios.dam.intermodulardam.ui.composable.MainTopBar
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.ui.theme.textStyleLogin
import ejercicios.dam.intermodulardam.utils.Constants
import ejercicios.dam.intermodulardam.utils.DisabledBrown
import ejercicios.dam.intermodulardam.utils.backgroundGreen

@Composable
fun PublicationView(navController: NavHostController, publicationViewModel: PublicationViewModel, commentsViewModel: CommentViewModel, id: String) {

    publicationViewModel.onInit(id)
    val userCreator by publicationViewModel.user.observeAsState()
    val currentUser by publicationViewModel.currentUser.observeAsState()
    val route by publicationViewModel.route.observeAsState()
    val comments by publicationViewModel.comments.observeAsState()
    val isLoading by publicationViewModel.isLoading.observeAsState()

    if(isLoading!!){
        WaitingScreen()
    }else{
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { MainTopBar(coroutineScope, scaffoldState) },
            content = {
                ContentPublicationView(
                    route = route!!,
                    user = userCreator!!,
                    currentUser = currentUser!!,
                    comments = comments!!,
                    commentsViewModel = commentsViewModel,
                    navController
                ) },
            bottomBar = { MainBottomBar(navController = navController) },
            drawerContent = { MainDrawer(navController = navController, currentUser!!, coroutineScope, scaffoldState) },
            drawerGesturesEnabled = false
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ContentPublicationView(route: Publication, user: User, currentUser: User, comments:List<Comment>, commentsViewModel: CommentViewModel, navController: NavHostController) {
    val message by commentsViewModel.message.observeAsState(initial = "")

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.backgroundGreen)
        .padding(5.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(3F)) {
            RouteCard(route, user)
        }
        Spacer(modifier = Modifier.height(3.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(0.8F)) {
            Card(elevation = 10.dp, modifier = Modifier.fillMaxWidth()) {
                Column() {
                    Row(modifier = Modifier
                        .fillMaxWidth()) {
                        Text(text = "Comentarios: ", fontSize = 28.sp, style = TextStyle(fontFamily = calibri, fontWeight = FontWeight.Bold))
                    }
                    Spacer(modifier = Modifier.height(1.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()) {
                        val scrollState = rememberScrollState()
                        LazyColumn(modifier = Modifier.scrollable(scrollState, Orientation.Vertical)) {
                            items(comments.size) {
                                CommentCard(currentUser, comments[it], commentsViewModel, route)
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(0.7F),
        horizontalArrangement = Arrangement.Center) {
            CommentField(message) {
                commentsViewModel.onCommentChanged(message = it)
            }
        }
        Spacer(modifier = Modifier.height(1.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(0.7F)) {
            CreateCommentButton(commentsViewModel, currentUser, route)
        }
    }
}

@Composable
fun RouteCard(route:Publication, user:User) {
    Card(elevation = 20.dp) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            Card(elevation = 20.dp) {
                if(route.photos.size > 0) {
                    PostPhoto(name = route.id + "/" + route.photos[0], Modifier.fillMaxWidth())
                } else {
                    PostPhoto(name = "noPhotos.png", Modifier.fillMaxWidth())
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            RouteUser(user = user, Modifier)
            Spacer(modifier = Modifier.height(5.dp))
            Description(route.description)
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.width(120.dp)) {
                    Duration(route.duration)
                }
                Column(horizontalAlignment = Alignment.End, modifier = Modifier.width(120.dp)) {
                    Difficulty(route.difficulty)
                }
            }

        }
    }
}

@Composable
fun CommentCard(user:User, comment: Comment, commentsViewModel: CommentViewModel, post:Publication) {
    val context = LocalContext.current

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = comment.message, fontSize = 18.sp, style = TextStyle(fontFamily = calibri))
            if(user.id == comment.user) {
                IconButton(onClick = { commentsViewModel.onDeleteComment(context, comment.id, post.id) }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Borrar Comentario", tint = Color.Red)
                }

            }
        }
    }

}

@Composable
fun RouteUser(user: User, modifier: Modifier) {
    Row(){
        Image(
            rememberAsyncImagePainter(model = "http://${Constants.IP_ADDRESS}/api/v1/imgs/users/${user.pfp_path}"),
            contentDescription = "User PFP",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(CircleShape)
                .size(26.dp)
                .align(Alignment.CenterVertically)
        )
        Text(text = user.nick, fontSize = 16.sp, fontFamily = calibri, modifier = Modifier
            .padding(10.dp)
            .align(Alignment.CenterVertically))
    }
}

@Composable
fun CommentField(comment: String, onTextChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(Color.White, Color.White)),
                shape = RoundedCornerShape(12.dp)
            )
            .width(300.dp),
        value = comment,
        maxLines = 1,
        onValueChange = { onTextChanged(it) },
        label = { PlaceholderForField("Comment") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            textColor = Color.White,
            cursorColor = Color.White
        ),
        textStyle = textStyleLogin
    )
}

@Composable
fun CreateCommentButton(commentsViewModel: CommentViewModel, user:User, post:Publication) {
    val context = LocalContext.current
    val isButtonEnabled by commentsViewModel.isButtonEnabled.observeAsState(initial = false)
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .absoluteOffset(y = -(24).dp), horizontalArrangement = Arrangement.Center) {
        Button(
            modifier = Modifier
                .width(240.dp)
                .height(60.dp),
            onClick = {commentsViewModel.onCreateComment(userID = user.id, postID = post.id, context)},
            shape = RoundedCornerShape(40.dp),
            border= BorderStroke(1.dp, Color.Black),
            enabled = isButtonEnabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.DisabledBrown,
                disabledBackgroundColor = Color(0xFF6a6a6a))
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "Comentar",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = calibri
            )
        }
    }
}

@Composable
fun Description(desc:String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = desc, fontSize = 14.sp, fontFamily = calibri)
    }
}

@Composable
fun Duration(duration:String) {
    Text(text = "Duration: $duration", fontSize = 14.sp, fontFamily = calibri)
}

@Composable
fun Difficulty(difficulty:String) {
    Text(text = "Difficulty: $difficulty", fontSize = 14.sp, fontFamily = calibri)
}

