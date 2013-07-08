package net.sourceforge.hstmixinsupport.sample;

import java.util.List;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;

@Mixin("test:CarouselBannerPicker")
public interface CarouselBannerPicker {
	
	@JcrPath("test:banner")
	public <T> List<Banner> getBanners();

}
