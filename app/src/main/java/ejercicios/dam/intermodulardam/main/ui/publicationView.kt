package ejercicios.dam.intermodulardam.main.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import ejercicios.dam.intermodulardam.R
import ejercicios.dam.intermodulardam.comments.domain.entity.Comment
import ejercicios.dam.intermodulardam.comments.ui.CommentViewModel
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.ui.composable.MainBottomBar
import ejercicios.dam.intermodulardam.ui.composable.MainDrawer
import ejercicios.dam.intermodulardam.ui.composable.MainTopBar
import ejercicios.dam.intermodulardam.ui.theme.backgroundGreen
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.utils.Constants.IP_ADDRESS
import ejercicios.dam.intermodulardam.utils.MainBrown
import java.text.SimpleDateFormat

@Composable
fun PublicationView(navController: NavHostController, publicationViewModel: PublicationViewModel, commentsViewModel: CommentViewModel, id: String) {

    LaunchedEffect(key1 = true){
        publicationViewModel.onInit(id)
        commentsViewModel.onInit(id)
    }

    val userCreator by publicationViewModel.user.observeAsState()
    val currentUser by publicationViewModel.currentUser.observeAsState()
    val route by publicationViewModel.route.observeAsState()
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
                    userCreator = userCreator!!,
                    commentsViewModel = commentsViewModel
                ) },
            bottomBar = { MainBottomBar(navController = navController) },
            drawerContent = { MainDrawer(navController = navController, currentUser!!, coroutineScope, scaffoldState) },
            drawerGesturesEnabled = false
        )
    }



}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentPublicationView(
    route: Publication,
    userCreator: User,
    commentsViewModel: CommentViewModel) {
    val canSeeComments by commentsViewModel.canSeeComments.observeAsState(false)
    val buttonValue by commentsViewModel.buttonValue.observeAsState("")
    LazyColumn(modifier = Modifier
        .background(backgroundGreen)
        .padding(5.dp)){

        stickyHeader {
            Column(Modifier.background(backgroundGreen)) {
                PublicationTitle(route.name, modifier = Modifier.align(Alignment.CenterHorizontally))
                PublicationPhotos(id = route.id, photos = route.photos)
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .height(2.dp)
                        .padding(20.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                    PublicationUser(userCreator, modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterVertically))
                    CommentsButton(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        buttonValue = buttonValue,
                        changeView = { commentsViewModel.seeCommentsView() })
                }
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .height(2.dp)
                        .padding(20.dp))
            }
        }
        item {
            if(canSeeComments!!){
                CommentsView(commentsViewModel = commentsViewModel)
            }else{
                PublicationCard(route = route)
            }
        }
    }
}

@Composable
fun CommentsView(commentsViewModel: CommentViewModel) {
    val context = LocalContext.current
    val comments by commentsViewModel.comments.observeAsState(mutableListOf())
    val creators by commentsViewModel.commentCreators.observeAsState(mutableListOf())
    val currentUser by commentsViewModel.user.observeAsState()
    val isLoading by commentsViewModel.isLoading.observeAsState(false)
    Column(
        Modifier
            .fillMaxSize()
            .defaultMinSize(minHeight = 300.dp)
            .padding(bottom = 35.dp)
            .background(backgroundGreen)){
        if(isLoading){
            WhiteWaitingScreen()
        }else{
            CreateCommentTextField(commentsViewModel = commentsViewModel)
            for (i in comments.indices){
                Log.d("COMENTS", creators!![i].nick)
                CommentCard(comment = comments[i], creator = creators!![i], currentUser = currentUser!!, onDeleteComment = {commentsViewModel.onDeleteComment(context = context,id = comments!![i].id)} )
            }
        }
    }
}

@Composable
fun CreateCommentTextField(commentsViewModel: CommentViewModel) {
    val message by commentsViewModel.message.observeAsState("")
    val isEnabled by commentsViewModel.isButtonEnabled.observeAsState(false)
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 10.dp)){
        TextField(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(listOf(Color.White, Color.White)),
                    shape = RoundedCornerShape(12.dp)
                )
                .width(300.dp),
            value = message,
            textStyle = TextStyle(
                fontFamily = calibri,
                fontSize = 14.sp,
                color = Color.White
            ),
            maxLines = 3,
            onValueChange = { commentsViewModel.onCommentChanged(it) },
            label = { Text(text = "Write a comment...", color = Color.LightGray) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color.White,
                cursorColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(onClick = { commentsViewModel.onCreateComment(context)}, enabled = isEnabled, modifier = Modifier
            .size(50.dp)
            .border(1.dp, Color.Black)
            .background(MaterialTheme.colors.MainBrown)) {
            Icon(painter = painterResource(id = R.drawable.sendcomment), contentDescription = "Send comment", tint = Color.White)
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun CommentCard(comment: Comment, creator: User, currentUser : User, onDeleteComment : () -> Unit) {
    Column(
        Modifier
            .padding(vertical = 10.dp)
            .background(backgroundGreen)){
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp)){
            Image(rememberAsyncImagePainter(model = "http://$IP_ADDRESS/api/v1/imgs/users/${creator.pfp_path}"), contentDescription = "User PFP", modifier = Modifier
                .size(60.dp)
                .clip(
                    CircleShape
                ), contentScale = ContentScale.Crop)
            Column{
                Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(horizontal = 15.dp)){
                    Text(
                        text = creator.nick,
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = calibri,
                        fontWeight = FontWeight.Bold,

                        )
                    Text(
                        text = SimpleDateFormat("dd/MM/yyyy").format(comment.date),
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        fontFamily = calibri,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
                Text(text = comment.message, color = Color.White, fontFamily = calibri, fontSize = 16.sp, textAlign = TextAlign.Justify, modifier = Modifier.padding(horizontal = 15.dp).width(230.dp))
            }
            if(comment.user == currentUser.id){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = { onDeleteComment() }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Borrar Comentario", tint = Color.Red)
                    }
                }
            }
        }
        Divider(
            Modifier
                .fillMaxWidth(), color = Color.White)
    }
}

@Composable
fun PublicationCard(route: Publication) {
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 5.dp, end = 5.dp, bottom = 60.dp)
            .background(backgroundGreen)){

        Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)){
            Column(Modifier.padding(end = 40.dp), horizontalAlignment = Alignment.CenterHorizontally){
                PublicationTitleParameter(title = "Category")
                PublicationValueParameter(value = route.category)
                Divider(
                    Modifier
                        .width(150.dp)
                        .padding(5.dp), color = Color.White)
                PublicationTitleParameter(title = "Duration")
                PublicationValueParameter(value = route.duration)
            }
            Divider(
                Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .padding(vertical = 10.dp)
                    .background(Color.White))
            Column(Modifier.padding(start = 40.dp), horizontalAlignment = Alignment.CenterHorizontally){
                PublicationTitleParameter(title = "Difficulty")
                PublicationValueParameter(value = route.difficulty)
                Divider(
                    Modifier
                        .width(150.dp)
                        .padding(5.dp), color = Color.White)
                PublicationTitleParameter(title = "Distance")
                PublicationValueParameter(value = route.distance)
            }
        }
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(1.dp)
            .background(Color.White))
        PublicationTitleParameter(title = "Description")
        PublicationValueParameter(value = route.description)

    }
}

@Composable
fun PublicationUser(userCreator: User, modifier: Modifier) {
    Row(horizontalArrangement = Arrangement.SpaceBetween){
        Image(
            rememberAsyncImagePainter(model = "http://$IP_ADDRESS/api/v1/imgs/users/${userCreator.pfp_path}"),
            contentDescription = "User PFP",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(CircleShape)
                .size(45.dp)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = userCreator.nick,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = calibri,
            modifier = modifier
                .align(Alignment.CenterVertically)
        )
    }
}
@Composable
fun CommentsButton(modifier: Modifier, changeView: () -> Unit, buttonValue : String){
    Button(
        onClick = { changeView() },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.MainBrown),
        shape = RoundedCornerShape(40.dp),
        border= BorderStroke(1.dp, Color.Black),
        modifier = modifier
    ) {
        Text(
            text = buttonValue,
            fontSize = 14.sp,
            color = Color.White,
            fontFamily = calibri,
        )
    }
}

@Composable
fun PublicationValueParameter(value: String) {
    Text(text = value, fontFamily = calibri, fontSize = 20.sp, modifier = Modifier.padding(vertical = 5.dp), color = Color.White, textAlign = TextAlign.Center)

}

@Composable
fun PublicationTitleParameter(title: String) {
    Text(text = title, fontFamily = calibri, fontSize = 25.sp, color = Color.Gray, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 5.dp))

}

@Composable
fun PublicationTitle(name: String, modifier: Modifier) {
    Text(
        text = name,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = calibri,
        fontSize = 27.sp,
        color = Color.White,
        modifier = modifier.padding(20.dp)
    )
}
@Composable
fun PublicationPhotos(id: String, photos: ArrayList<String>) {
    var indexPhoto by rememberSaveable{ mutableStateOf(0) }

    val url : String
    val isEnabled : Boolean

    if(photos.size > 0){
        url = "http://$IP_ADDRESS/api/v1/imgs/posts/$id/${photos[indexPhoto]}"
        isEnabled = true
    }else{
        isEnabled = false
        url = "http://$IP_ADDRESS/api/v1/imgs/posts/noPhotos.png"
    }

    Box(contentAlignment = Alignment.Center ,modifier = Modifier
        .fillMaxWidth()){
        Image(
            rememberAsyncImagePainter(model = url),
            contentDescription = "PostPhoto",
            Modifier
                .fillMaxWidth()
                .height(240.dp),
            contentScale = ContentScale.Fit
        )
        IconButton(onClick = { indexPhoto = changePhoto(max = photos.size, actual = indexPhoto, change = -1) }, enabled = isEnabled,modifier = Modifier
            .align(Alignment.CenterStart)
            .background(color = backgroundGreen, shape = RoundedCornerShape(30.dp))
        ) {
            Icon(painter = painterResource(id = R.drawable.leftarrow), contentDescription = "Next photo", tint = Color.White)
        }

        IconButton(onClick = { indexPhoto = changePhoto(max = photos.size, actual = indexPhoto, change = 1) } , enabled = isEnabled, modifier = Modifier
            .align(Alignment.CenterEnd)
            .background(color = backgroundGreen, shape = RoundedCornerShape(30.dp))
        ) {
            Icon(painter = painterResource(id = R.drawable.rightarrow), contentDescription = "Previous photo", tint = Color.White)
        }
    }
}

fun changePhoto(max: Int, actual: Int, change: Int): Int {
    var newValue = actual+change
    if(newValue < 0){
        newValue = max - 1
    }else if(newValue >= max) {
        newValue = 0
    }
    return newValue
}

@Composable
fun WhiteWaitingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .align(Alignment.CenterHorizontally)) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color.White)
        }
    }
}