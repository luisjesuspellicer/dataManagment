/**
 * Author: Eugen Paraschiv
 * Modified by Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the service for manage ip's with bad credentials.
 */
package tfg.backend.Configuration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class AuthenticationService {

    //Num of fail authentication that the system let before block.
    private final int MAX = 5;
    private LoadingCache<String, Integer> times;

    public AuthenticationService() {
        times = CacheBuilder.newBuilder().expireAfterWrite(2,
                TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }

    public boolean isBlocked(String key){
        try {

            return times.get(key) >= MAX;

        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Pre: key != null
     * Post: Increase the number of fail intentd
     * @param key
     */
    public void authenticationFailed(String key){
        int numTimes = 0;

        try {
            numTimes = times.get(key);
        } catch (ExecutionException e) {
            numTimes = 0;
            e.printStackTrace();
        }
        finally {
            numTimes ++;
            times.put(key,numTimes);
    }


    }
    public void authenticationSuccessful(String key) {
        times.invalidate(key);
    }
}
