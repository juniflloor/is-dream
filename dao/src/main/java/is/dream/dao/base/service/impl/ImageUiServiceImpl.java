package is.dream.dao.base.service.impl;

import is.dream.dao.base.service.ImageUiService;
import is.dream.dao.entiry.ImageUi;
import is.dream.dao.inter.ImageUiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 20:17
 */
@Service
public class ImageUiServiceImpl implements ImageUiService {

    @Autowired
    private ImageUiDao imageUiDao;

    @Override
    public void save(ImageUi imageUi) {
        imageUiDao.save(imageUi);
    }
}
