package systems.kuu.licenseaggregator.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange

@Composable
fun LicenseScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        val identifier = context.resources.getIdentifier("dependencies.json", "raw", context.packageName)
        val dependenciesJson = context.resources.openRawResource(identifier)
        val jsonFile = dependenciesJson.readBytes().joinToString(separator = "")

        Text("license screen")
    }
}