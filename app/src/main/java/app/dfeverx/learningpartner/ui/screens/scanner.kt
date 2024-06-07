package app.dfeverx.learningpartner.ui.screens

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException

@Composable
fun Scanner(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(
        defaultElevation = 0.dp
    ),
    rawPdfUri: (Uri,String) -> Unit
) {
    val options = GmsDocumentScannerOptions.Builder()
        .setGalleryImportAllowed(false)
        .setPageLimit(2)
        .setResultFormats(
            GmsDocumentScannerOptions.RESULT_FORMAT_JPEG,
            GmsDocumentScannerOptions.RESULT_FORMAT_PDF
        )
        .setScannerMode(GmsDocumentScannerOptions.SCANNER_MODE_BASE)

        .setGalleryImportAllowed(true)
        .build()

    val activity = LocalContext.current as ComponentActivity
    val scanner = GmsDocumentScanning.getClient(options)
    // When using Latin script library
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val scannerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val documentScanningResult =
                GmsDocumentScanningResult.fromActivityResultIntent(
                    result.data
                )
            documentScanningResult?.pages?.let { pages ->
                for (page in pages) {
                    val imageUri = page.imageUri
                    Log.d("TAG", "DocumentScanner: $imageUri")
                    val image: InputImage
                    try {
                        image = InputImage.fromFilePath(activity, imageUri)
                        recognizer.process(image)
                            .addOnSuccessListener { visionText ->
                                // Task completed successfully
                                // ...
                                Log.d("TAG", "DocumentScanner: text ${visionText.text}")
                                rawPdfUri(imageUri,visionText.text)
                            }
                            .addOnFailureListener { e ->
                                // Task failed with an exception
                                // ...
                                Log.d("TAG", "DocumentScanner: error text ${e.message}")
                            }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    // Handle the image URI
                }
            }
            documentScanningResult?.pdf?.let { pdf ->
                val pdfUri = pdf.uri
                val pageCount = pdf.pageCount
                Log.d("TAG", "DocumentScanner: $pdfUri")
//                rawPdfUri(pdfUri)
                // Handle the PDF URI and page count
            }
        }
    }
    ExtendedFloatingActionButton(
        modifier = modifier
            .height(72.dp)
            .widthIn(min = 72.dp)
            .animateContentSize(),
        expanded = expanded,
        onClick = {
            scanner.getStartScanIntent(activity)
                .addOnSuccessListener { intentSender ->
                    scannerLauncher.launch(
                        IntentSenderRequest.Builder(intentSender).build()
                    )
                }.addOnFailureListener {
                    Log.d("TAG", "DocumentScanner: Error :${it.message}")
                }
        },
        icon = { Icon(Icons.Outlined.DocumentScanner, "Extended floating action button.") },
        text = { Text(text = "Scan notes") },

        elevation = elevation
    )

}
