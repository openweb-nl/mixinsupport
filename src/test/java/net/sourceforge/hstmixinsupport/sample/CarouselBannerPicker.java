package net.sourceforge.hstmixinsupport.sample;

import java.util.List;

import net.sourceforge.hstmixinsupport.annotations.Path;
import net.sourceforge.hstmixinsupport.annotations.Mixin;

@Mixin("test:CarouselBannerPicker")
public interface CarouselBannerPicker {
	
	@Path("test:banner")
	public <T> List<Banner> getBanners();

}
