package is.dream.dao.base.service.impl;

import is.dream.dao.base.service.UserLikeVideoService;
import is.dream.dao.entiry.UserLikeVideo;
import is.dream.dao.inter.UserLikeVideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/11 15:25
 */
@Service
public class UserLikeVideoServiceImpl implements UserLikeVideoService {

    @Autowired
    private UserLikeVideoDao userLikeVideoDao;

    @Override
    public void save(UserLikeVideo userLikeVideo) {
        userLikeVideoDao.save(userLikeVideo);
    }

    @Override
    public List<UserLikeVideo> getByAssociatedUserId(String associatedUserId) {
        return userLikeVideoDao.getByAssociatedUserId(associatedUserId);
    }
}
