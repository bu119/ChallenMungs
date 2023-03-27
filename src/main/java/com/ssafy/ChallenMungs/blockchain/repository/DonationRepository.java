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
}
