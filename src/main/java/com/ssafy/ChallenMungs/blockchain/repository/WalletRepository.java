package com.ssafy.ChallenMungs.blockchain.repository;

import com.ssafy.ChallenMungs.blockchain.entity.Wallet;
import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
   List <Wallet> findAllByUser(User user);

   Wallet findByUserAndType(User user,char type);
}
