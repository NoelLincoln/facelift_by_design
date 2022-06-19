package com.facelift.admin.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.facelift.common.entity.setting.Setting;
import com.facelift.common.entity.setting.SettingCategory;

public interface SettingRepository extends CrudRepository<Setting, String> {
	public List<Setting> findByCategory(SettingCategory category);
}
