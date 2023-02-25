package ejercicios.dam.intermodulardam.profile.domain

import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.profile.data.ProfileRepository
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(private val repository:ProfileRepository) {
    suspend operator fun invoke(user: User):Boolean {
        return repository.updateUser(user)
    }
}