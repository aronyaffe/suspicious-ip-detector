package dal

interface IDataBase {
    fun getAllSuspiciousIps(): List<String>
}