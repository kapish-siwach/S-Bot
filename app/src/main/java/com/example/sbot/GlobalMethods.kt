package com.example.sbot


import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import io.noties.markwon.Markwon
import java.util.Calendar


@Composable
fun ChatBubble(item: ChatItem) {
    val backgroundColor = when (item.userType) {
        UserType.USER -> Color(0xFFD3F6B7)
        UserType.AI -> Color(0xFFE5E5E5)
    }
    val padding= if(item.userType == UserType.USER){
        PaddingValues(5.dp)
    }else{
        PaddingValues(start = 5.dp, top = 5.dp, bottom = 5.dp, end = 25.dp)
    }
    val alignment = if (item.userType == UserType.USER) {
        Alignment.End
    } else {
        Alignment.Start
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .wrapContentWidth(alignment)
    ) {
        MarkdownTextView(item.text, backgroundColor)
//        Text(
//            text = item.text,
//            modifier = Modifier
//                .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
//                .padding(10.dp),
//            fontSize = 16.sp
//        )
    }
}

@Composable
fun MarkdownTextView(markdown: String, backgroundColor: Color) {
    val context = LocalContext.current
    val markwon = remember { Markwon.create(context) }

    AndroidView(
        factory = {
            TextView(context).apply {
                setTextColor(android.graphics.Color.BLACK)
                setPadding(24, 16, 24, 16)
            }
        },
        update = { textView ->
            markwon.setMarkdown(textView, markdown)
        },
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(4.dp)
    )
}

@Composable
fun MessageList(messages: List<ChatItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(2.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        items(messages) { item ->
            ChatBubble(item)
        }
    }
}
