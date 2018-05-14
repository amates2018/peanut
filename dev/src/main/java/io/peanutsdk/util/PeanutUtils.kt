package io.peanutsdk.util

import android.net.Uri
import com.liulishuo.okdownload.DownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.cause.ResumeFailedCause
import com.liulishuo.okdownload.core.listener.DownloadListener1
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist
import java.lang.Exception

/**
 * Project : peanut
 * Created by Dennis Bilson on Mon at 11:07 PM.
 * Package name : io.peanutsdk.util
 */
object PeanutUtils {
	
	fun doDownload(url: String, uri: Uri, filename: String) {
		val task = DownloadTask.Builder(url, uri)
				.setFilename(filename)
				// the minimal interval millisecond for callback progress
				.setMinIntervalMillisCallbackProcess(30)
				// do re-download even if the task has already been completed in the past.
				//.setPassIfAlreadyCompleted(false)
				.build()
		
		val listener: DownloadListener = object : DownloadListener1() {
			override fun taskStart(task: DownloadTask, model: Listener1Assist.Listener1Model) {}
			
			override fun taskEnd(task: DownloadTask, cause: EndCause, realCause: Exception?, model: Listener1Assist.Listener1Model) {}
			
			override fun progress(task: DownloadTask, currentOffset: Long, totalLength: Long) {}
			
			override fun connected(task: DownloadTask, blockCount: Int, currentOffset: Long, totalLength: Long) {}
			
			override fun retry(task: DownloadTask, cause: ResumeFailedCause) {}
		}
		
		DownloadTask.enqueue(arrayOf(task), listener)
	}
}
