package ro.alexmamo.firebasesigninwithemailandpassword.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ro.alexmamo.firebasesigninwithemailandpassword.data.repository.AuthRepositoryImpl
import ro.alexmamo.firebasesigninwithemailandpassword.domain.repository.AuthRepository

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(
        auth = auth
    )
}