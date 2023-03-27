package com.ssafy.ChallenMungs.blockchain.repository;

import com.ssafy.ChallenMungs.blockchain.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
