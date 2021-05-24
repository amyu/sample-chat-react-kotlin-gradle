package domain.user

interface GetMeService {
    suspend fun get(): User.Me?
}