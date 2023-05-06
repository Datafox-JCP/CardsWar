package mx.datafox.cardswar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import mx.datafox.cardswar.ui.theme.CardsWarTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current

            (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            CardsWarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BackgroundBox()
                    GamePlay()
                }
            }
        }
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun GamePlay() {
    val context = LocalContext.current
    val mediaPlayerSpin = MediaPlayer.create(context, R.raw.spin)

    var playerCard by remember { mutableStateOf(13) }
    var cpuCard by remember { mutableStateOf(13) }

    val playerImageResource = when (playerCard) {
        1 -> R.drawable.card2
        2 -> R.drawable.card3
        3 -> R.drawable.card4
        4 -> R.drawable.card5
        5 -> R.drawable.card6
        6 -> R.drawable.card7
        7 -> R.drawable.card8
        8 -> R.drawable.card9
        9 -> R.drawable.card10
        10 -> R.drawable.card11
        11 -> R.drawable.card12
        12 -> R.drawable.card13
        else -> R.drawable.card14
    }

    val cpuImageResource = when (cpuCard) {
        1 -> R.drawable.card2
        2 -> R.drawable.card3
        3 -> R.drawable.card4
        4 -> R.drawable.card5
        5 -> R.drawable.card6
        6 -> R.drawable.card7
        7 -> R.drawable.card8
        8 -> R.drawable.card9
        9 -> R.drawable.card10
        10 -> R.drawable.card11
        11 -> R.drawable.card12
        12 -> R.drawable.card13
        else -> R.drawable.card14
    }

    var playerScore by remember { mutableStateOf(0) }
    var cpuScore by remember { mutableStateOf(0) }

    Column{
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Logo()
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(painterResource(
                id = playerImageResource),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(32.dp))
            Image(painterResource(
                id = cpuImageResource),
                contentDescription = ""
            )
        }

        Spacer(modifier = Modifier.height(64.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    playerScore = 0
                    cpuScore = 0

                    playerCard = 13
                    cpuCard = 13
                },
                modifier = Modifier
                    .height(60.dp)
                    .width(120.dp)
            ) {
                Text(text = stringResource(R.string.reset))
            }

            Spacer(modifier = Modifier.width(32.dp))

            Button(
                onClick = {
                    playerCard = Random.nextInt(2, 14)
                    val playerRandom = playerCard

                    cpuCard = Random.nextInt(2, 14)
                    val cpuRandom = cpuCard

                    if (playerRandom > cpuRandom) {
                        playerScore += 1
                    } else if (cpuRandom > playerRandom) {
                        cpuScore += 1
                    }

                    ContextCompat.getSystemService(context, Vibrator::class.java)?.vibrate(
                        VibrationEffect.createOneShot(
                            100,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )

                    Thread {
                        mediaPlayerSpin.start()
                    }.start()

                },
                modifier = Modifier
                    .height(60.dp)
                    .width(120.dp)
            ) {
                Text(text = stringResource(R.string.play))
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(R.string.player),
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$playerScore",
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.width(32.dp))
            Text(text = stringResource(R.string.device),
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$cpuScore",
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Composable
fun Logo() {
    Image(painterResource(
        id = R.drawable.logo),
        contentDescription = "",
        modifier = Modifier
            .size(200.dp)
    )
}

@Composable
fun BackgroundBox() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(painterResource(
            id = R.drawable.background),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    CardsWarTheme {
        BackgroundBox()
        GamePlay()
    }
}