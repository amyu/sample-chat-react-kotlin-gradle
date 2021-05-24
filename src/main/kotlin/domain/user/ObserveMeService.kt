package domain.user

import kotlinx.coroutines.flow.Flow

interface ObserveMeService {
    fun observe(): Flow<User.Me?>
}