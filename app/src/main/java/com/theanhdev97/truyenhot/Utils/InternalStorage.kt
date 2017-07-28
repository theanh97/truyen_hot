package com.theanhdev97.truyenhot.Utils

import android.content.Context
import android.widget.Toast
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.theanhdev97.truyenhot.Model.Truyen
import java.io.*


/**
 * Created by DELL on 17/07/2017.
 */
class InternalStorage {
    companion object {
        fun saveTruyen(context: Context, fileName: String, truyen: Truyen) {
            val mode = Context.MODE_PRIVATE
            val fileOutputStream: FileOutputStream
            val objectOutputStream: ObjectOutputStream
            try {
                // write
                fileOutputStream = context.openFileOutput(fileName, mode)
                objectOutputStream = ObjectOutputStream(fileOutputStream)
                objectOutputStream.writeObject(truyen)
                objectOutputStream.flush()

                // release memory
                objectOutputStream.close()
                fileOutputStream.getFD().sync()
                fileOutputStream.close()
            } catch (e: FileNotFoundException) {
                Log.d(Constants.TAG, "Error : ${e.message}")
            } catch (e: IOException) {
                Log.d(Constants.TAG, "Error : ${e.message}")
            }
        }

        fun getTruyen(context: Context, fileName: String): Truyen {
            var truyen: Truyen? = null
            val fis: FileInputStream
            try {
                fis = context.openFileInput(fileName)
                val objectInputStream = ObjectInputStream(fis)
                truyen = objectInputStream.readObject() as Truyen

                objectInputStream.close()
                fis.close()
            } catch (e: FileNotFoundException) {
                Log.e("InternalStorage", e.message)
            } catch (e: IOException) {
                Toast.makeText(context, e.getStackTrace().toString(), Toast.LENGTH_SHORT).show()
            } catch (e: ClassNotFoundException) {
                Toast.makeText(context, e.stackTrace.toString(), Toast.LENGTH_SHORT).show()
            }

            return truyen!!
        }
    }
}