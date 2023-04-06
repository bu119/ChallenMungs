package com.ssafy.challenmungs.data.remote.repository

import com.ssafy.challenmungs.common.util.wrapToResource
import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.data.remote.datasource.challenge.ChallengeRemoteDataSource
import com.ssafy.challenmungs.domain.entity.challenge.*
import com.ssafy.challenmungs.domain.repository.ChallengeRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val challengeRemoteDataSource: ChallengeRemoteDataSource
) : ChallengeRepository {

    override suspend fun getChallengeList(
        type: Int,
        searchValue: String?,
        lat: Double,
        lng: Double
    ): Resource<List<Challenge>> = wrapToResource(Dispatchers.IO) {
        challengeRemoteDataSource.getChallengeList(
            type,
            searchValue,
            lat,
            lng
        ).ListNotStarted.map { it.toDomainModel() }
    }

    override suspend fun getChallengeInfo(challengeId: Int): Resource<NotStartedChallengeDetail> =
        wrapToResource(Dispatchers.IO) {
            challengeRemoteDataSource.getChallengeInfo(challengeId).toDomainModel()
        }

    override suspend fun getBasicToday(challengeId: Int): Resource<List<ChallengeBasicToday>> =
        wrapToResource(Dispatchers.IO) {
            challengeRemoteDataSource.getBasicToday(challengeId).map { it.toDomainModel() }
        }

    override suspend fun getBasicHistory(
        challengeId: Int,
        targetMemberId: String
    ): Resource<List<ChallengeBasicHistory>> =
        wrapToResource(Dispatchers.IO) {
            challengeRemoteDataSource.getBasicHistory(challengeId, targetMemberId).map {
                it.toDomainModel()
            }
        }

    override suspend fun getParticipants(challengeId: Int): Resource<List<ChallengeMember>> =
        wrapToResource(Dispatchers.IO) {
            challengeRemoteDataSource.getParticipants(challengeId).map { it.toDomainModel() }
        }

    override suspend fun getChallengeParticipationFlag(challengeId: Long): Resource<Boolean> =
        wrapToResource(Dispatchers.IO) {
            challengeRemoteDataSource.getChallengeParticipationFlag(challengeId).toDomainModel()
                .toBoolean()
        }

    override suspend fun requestParticipate(challengeId: Long, teamId: Int?): Resource<String> =
        wrapToResource(Dispatchers.IO) {
            challengeRemoteDataSource.requestParticipate(challengeId, teamId).toDomainModel()
        }

    override suspend fun requestWithDraw(challengeId: Long): Resource<String> =
        wrapToResource(Dispatchers.IO) {
            challengeRemoteDataSource.requestWithDraw(challengeId).toDomainModel()
        }

    override suspend fun requestReject(boardId: Int): Resource<String> =
        wrapToResource(Dispatchers.IO) {
            challengeRemoteDataSource.requestReject(boardId).toDomainModel()
        }
}