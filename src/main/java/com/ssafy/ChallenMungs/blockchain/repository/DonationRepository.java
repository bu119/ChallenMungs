package com.ssafy.ChallenMungs.blockchain.repository;

import com.ssafy.ChallenMungs.Test.entity.Test;
import com.ssafy.ChallenMungs.blockchain.entity.Donation;
import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findAllByUserAndYearOrderByDonateDateDesc( User user, int year);
    Donation findByDonationId(int donationId);
    int countByUserAndYear(User user,int year);

    @Query(value = "select sum(money) from donation where login_id= :login_id",
            nativeQuery = true)
    int sumTotalAmount(@Param("login_id") String loginId);

    @Query(value = "select sum(money) from donation where login_id= :login_id and year= :year",
            nativeQuery = true)
    int sumYearAmount(@Param("login_id") String loginId,@Param("year") int year);

    @Query(value = "select count(*) from campaign where name = :name and wallet_address != 'none'",
            nativeQuery = true)
    int getRunningCampaignCnt(@Param("name") String name);

}
