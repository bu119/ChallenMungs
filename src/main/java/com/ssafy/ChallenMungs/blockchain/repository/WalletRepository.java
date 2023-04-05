package com.ssafy.ChallenMungs.blockchain.repository;

import com.ssafy.ChallenMungs.blockchain.entity.Wallet;
import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

   String normalChallenge = "0x2649eadC4C15bac554940A0A702fa759bddf0dBe";
   String specialChallenge = "0xee43BB5476e52B04175d698C56cC4516b96A85A5";
   List <Wallet> findAllByUser(User user);

   Wallet findByUserAndType(User user,char type);

   Wallet findByAddress(String address);

}
