package teikyo.mcc.CampusChase.data;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    // ルームごとのユーザー情報（仮DB）
    private static Map<String, List<Map<String, Object>>> roomUsers = new HashMap<>();

    // ユーザー追加（RoomControllerから呼ばれる）
    public static void addUser(String roomId, Map<String, Object> user) {
        roomUsers.putIfAbsent(roomId, new ArrayList<>());
        roomUsers.get(roomId).add(user);
    }

    // チーム振り分け
    @PostMapping("/rooms/{roomId}/teams/assign")
    public List<Map<String, Object>> assignTeams(
            @PathVariable String roomId,
            @RequestBody Map<String, Integer> request
    ) {
        int hunterCount = request.get("hunterCount");

        List<Map<String, Object>> users = roomUsers.get(roomId);

        if (users == null || users.size() == 0) {
            throw new RuntimeException("ユーザーがいない");
        }

        if (hunterCount <= 0 || hunterCount >= users.size()) {
            throw new RuntimeException("人数がおかしい");
        }

        // シャッフル（ランダム）
        Collections.shuffle(users);

        // 振り分け
        for (int i = 0; i < users.size(); i++) {
            if (i < hunterCount) {
                users.get(i).put("team", "HUNTER");
            } else {
                users.get(i).put("team", "RUNNER");
            }
        }

        return users;
    }
}