package teikyo.mcc.CampusChase;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import teikyo.mcc.CampusChase.data.*;


@RestController
public class UserController {
    @RequestMapping(value="/api/player",method=RequestMethod.POST)
    public UserOutput addUser(@RequestBody UserInput input) {
        UserOutput u = new UserOutput();
        //仮実装
        u.setId(0);
        u.setName("test");
        return u;
    }
}
