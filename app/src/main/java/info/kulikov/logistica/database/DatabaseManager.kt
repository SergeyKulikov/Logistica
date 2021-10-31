package info.kulikov.logistica.database


import info.kulikov.logistica.Somewhat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DatabaseManager {

    fun loadSomewhatRepos(dao: AppDao) =
        dao.loadSomewhatList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun saveSomewhatRepos(dao: AppDao, userList: List<Somewhat>) =
        userList.let {
            dao.saveSomewhatList(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

}