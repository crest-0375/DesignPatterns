import java.io.File
import kotlin.test.Test

interface EventListener {
    fun update(eventType: String?, file: File)
}

class EventManager(vararg operations: String) {
    var listeners = hashMapOf<String, ArrayList<EventListener>>()
    init {
        for (operation in operations) {
            listeners[operation] = ArrayList()
        }
    }
    fun subscribe(eventType: String?, listener: EventListener) {
        val users = listeners[eventType]
        users?.add(listener)
    }
    fun unSubscribe(eventType: String?, listener: EventListener) {
        val users = listeners[eventType]
        users?.remove(listener)
    }
    fun notify(eventType: String?, file: File) {
        val users = listeners[eventType]
        users?.let {
            for (listener in it) {
                listener.update(eventType, file)
            }
        }
    }
}

class Editor {
    var events: EventManager = EventManager("open", "save")
    private var file: File? = null
    fun openFile(filePath: String) {
        file = File(filePath)
        events.notify("open", file!!)
    }
    fun saveFile() {
        if (file != null) {
            events.notify("save", file!!)
        }
    }
}

class EmailNotificationListener(private val email: String) : EventListener {
    override fun update(eventType: String?, file: File) {
        println("Email to $email Someone has performed $eventType with the file ${file.name}")
    }
}

class LogOpenListener(var fileName: String) : EventListener {
    override fun update(eventType: String?, file: File) {
        println("Save to log $fileName: Someone has performed $eventType operation with the file ${file.name}")
    }
}

class ObserverTest {
    @Test
    fun testObserver() {
        val editor = Editor()

        val logListener = LogOpenListener("path/to/log/file.txt")
        val emailListener = EmailNotificationListener("test@test.com")

        editor.events.subscribe("open", logListener)
        editor.events.subscribe("open", emailListener)
        editor.events.subscribe("save", emailListener)

        editor.openFile("test.text")
        editor.saveFile()
    }
}