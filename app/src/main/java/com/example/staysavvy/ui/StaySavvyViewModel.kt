package com.example.staysavvy.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staysavvy.data.HotelCategoryItem
import com.example.staysavvy.network.StaySavvyApi
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call

class StaySavvyViewModel():ViewModel() {

    private val _user=MutableStateFlow<FirebaseUser?>(null)
    val user:MutableStateFlow<FirebaseUser?>get() = _user

    private val _isVisible=MutableStateFlow(true)
    val isVisible:MutableStateFlow<Boolean>get() = _isVisible

    var itemUiState:ItemUiState by mutableStateOf(ItemUiState.Loading)
        private set

    private val _name= MutableStateFlow("")
    val name:MutableStateFlow<String>get() = _name

    private val _phoneNumber= MutableStateFlow("")
    val phoneNumber:MutableStateFlow<String>get() = _phoneNumber

    private val _otp=MutableStateFlow("")
    val  otp:MutableStateFlow<String>get() = _otp

    private val _email= MutableStateFlow("")
    val email:MutableStateFlow<String>get() = _email

    private val _verificationId=MutableStateFlow("")
    val verificationId:MutableStateFlow<String>get() = _verificationId

    private val _ticks =MutableStateFlow(60L)
    val ticks :MutableStateFlow<Long>get() = _ticks

    private val _loading=MutableStateFlow(false)
    val loading :MutableStateFlow<Boolean>get() = _loading

    private val _place=MutableStateFlow("")
    val place:MutableStateFlow<String>get() = _place

    private val _numberOfRooms=MutableStateFlow("")
    val numberOfRoom:MutableStateFlow<String>get() = _numberOfRooms

    private val _checkIn=MutableStateFlow("")
    val checkIn:MutableStateFlow<String>get() = _checkIn

    private val _checkOut=MutableStateFlow("")
    val checkOut:MutableStateFlow<String>get() = _checkOut

    private val _cartItems=MutableStateFlow<List<HotelCategoryItem>>(emptyList())
    val cartItems: StateFlow<List<HotelCategoryItem>> get() = _cartItems.asStateFlow()

    private val _logoutClicked=MutableStateFlow(false)
    val logoutClicked:MutableStateFlow<Boolean>get() = _logoutClicked


    private lateinit var timerJob: Job
    private lateinit var screenJob:Job
    private lateinit var internetJob:Job

    fun setUser(user: FirebaseUser){
        _user.value=user
    }

    fun setName(name:String){
        _name.value=name
    }

    fun setPhoneNumber(phoneNumber:String){
        _phoneNumber.value=phoneNumber
    }

    fun setOtp(otp:String){
        _otp.value=otp
    }

    fun setEmail(email:String){
        _email.value=email
    }

    fun setVerificationId(verificationId:String){
        _verificationId.value=verificationId
    }

    fun setPlace(place:String){
        _place.value=place
    }

    fun setNumberOfRooms(room:String){
        _numberOfRooms.value=room
    }

    fun setCheckIn(checkIn:String){
        _checkIn.value=checkIn
    }

    fun setCheckOut(checkOut:String){
        _checkOut.value=checkOut
    }

    fun setLogoutStatus(
        logoutStatus:Boolean
    ){
        _logoutClicked.value=logoutStatus
    }

    fun clearData(){
        _user.value=null
        _phoneNumber.value=""
        _otp.value=""
        verificationId.value=""
        resetTimer()
    }

    fun runTimer(){
        timerJob=viewModelScope.launch {
            while (_ticks.value>0){
                delay(1000)
                _ticks.value-=1
            }
        }
    }

    fun setLoading(isLoading:Boolean){
        _loading.value=isLoading
    }

    fun resetTimer(){
        try {
            timerJob.cancel()
        }catch (exception:Exception){

        }finally {
            _ticks.value=60
        }
    }

    fun toggleVisibility(){
        _isVisible.value=false
    }

    sealed interface ItemUiState{
        data class Success(val items: List<HotelCategoryItem>):ItemUiState
        object Error:ItemUiState
        object Loading:ItemUiState
    }

    fun getStaySavvyItem(){
        internetJob=viewModelScope.launch(Dispatchers.Default) {
            try {
                val listResult=StaySavvyApi.retrofitService.getItems()
                itemUiState=ItemUiState.Success(listResult)
            }
            catch (exception:Exception){
                itemUiState=ItemUiState.Error
                toggleVisibility()
                screenJob.cancel()
            }
        }
    }

    init {
        screenJob=viewModelScope.launch {
            delay(3000)
            toggleVisibility()
        }
        getStaySavvyItem()
    }

}