package dal

class DataBase:IDataBase {
    private var suspiciousIps: List<String> = listOf("12.13.15.16/26", "98.66.74.58/15", "99.88.44.77/28", "32.155.75.166/22")
    override fun getAllSuspiciousIps(): List<String> {
        return suspiciousIps
    }
}