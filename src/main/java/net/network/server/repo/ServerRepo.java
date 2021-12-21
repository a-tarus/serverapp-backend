package net.network.server.repo;

import net.network.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepo extends JpaRepository<Server, Long>{
    //Uniquely looking through all the ipAddress
    Server findByIpAddress(String ipAddress);
}
