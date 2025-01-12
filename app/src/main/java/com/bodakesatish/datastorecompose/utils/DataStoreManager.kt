package com.bodakesatish.datastorecompose.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bodakesatish.datastorecompose.model.UserDetail
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit

const val USER_DATASTORE = "user_data"

val Context.preferenceDataStore : DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

class DataStoreManager(val context: Context) {

    companion object {
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_MOBILE = stringPreferencesKey("user_mobile")
        val USER_PASSWORD = stringPreferencesKey("user_password")
        val USER_NAME = stringPreferencesKey("user_name")
    }

    suspend fun saveToDataStore(userDetail: UserDetail) {
        context.preferenceDataStore.edit {
            it[USER_EMAIL] = userDetail.emailAddress
            it[USER_MOBILE] = userDetail.mobileNumber
            it[USER_PASSWORD] = userDetail.password
            it[USER_NAME] = userDetail.name
        }
    }

    fun getFromDataStore() = context.preferenceDataStore.data.map {
        UserDetail(
            emailAddress = it[USER_EMAIL] ?: "",
            mobileNumber = it[USER_MOBILE] ?: "",
            password = it[USER_PASSWORD] ?: "",
            name = it[USER_NAME] ?: ""
        )
    }

    suspend fun clearDataStore() = context.preferenceDataStore.edit {
        it.clear()
    }

}