<!-- 
@author Laxman (laxman@sdrc.co.in)
 -->
<!--logo part end-->
<!-- spinner -->

 
	<div id="spinner" class="loader" style="display: none;"></div>
	<div id="loader-mask" class="loader" style="display: none;"></div>
<!-- /spinner -->
<nav class="navbar nav-menu-container menu-box home-menu-font">
	<button class="navbar-toggle custom-navbar-mobile" style="z-index: 777"
		data-toggle="collapse" data-target=".navbar-menu-collapse">
		<span class="icon-bar"></span> <span class="icon-bar"></span> <span
			class="icon-bar"></span>
	</button>
	<div class="container nav-section">
		<div class="col-md-1 navbar-header">
			<div class="logoresize">
				<div class="heading_partDesktop heading_part">
 					<div><a
					href="home"><img class="inner-slr-img" src="resources/images/slr_logo_strip1.svg" style="height:48px;"></a></div> 
				</div>
			</div>
		</div>
		<div
			class="col-md-7 header-margin navHeaderCollapse2 navbar-menu-collapse collapse navbar-collapse">
			
			<ul class="nav navbar-nav navbar-right nav-submenu nav-place-right">
<!-- 			<li><img class="inner-slr-img" src="resources/images/slr_logo_strip.svg" style="height:48px;margin-right: 351px;"></li>  -->
				<li ng-class="{'active' : activeMenu == 'home'}"><a href="home">
<!-- 				<img alt="" -->
<!-- 						ng-src="{{activeMenu == 'home' && 'resources/images/icons/icon_home_gray.svg' || 'resources/images/icons/icon_home_red.svg'}}"> -->
					<div>&nbsp;Home</div></a></li>
					<li ng-class="{'active' : activeMenu == 'about'}"><a href="home#about">
<!-- 					<img -->
<!-- 						alt="" -->
<!-- 						ng-src="{{activeMenu == 'about' && 'resources/images/icons/icon_home_gray.svg' || 'resources/images/icons/icon_home_red.svg'}}"> -->
					<div>&nbsp;About Us</div></a></li>
					
				<li ng-class="{'active' : activeMenu == 'dashboard'}"><a
					href="dashboard">
<!-- 					<img alt="" -->
<!-- 						ng-src="{{activeMenu == 'dashboard' && 'resources/images/icons/icon_dashboard_grey.svg' || 'resources/images/icons/icon_dashboard_red.svg'}}" -->
<!-- 						style="width: 26px;"> -->
					<div>&nbsp;Dashboard</div></a></li>
				<li ng-class="{'active' : activeMenu == 'advanceSearch'}"><a
					href="advanceSearch">
<!-- 					<img alt="" -->
<!-- 						ng-src="{{activeMenu == 'advanceSearch' && 'resources/images/icons/icon_advance_search_grey.svg' || 'resources/images/icons/icon_advance_search_red.svg'}}" -->
<!-- 						style="width: 16px;"> -->
					<div>&nbsp;Advanced Search</div></a></li>
				<li ng-class="{'active' : activeMenu == 'report'}"><a
					href="report">
<!-- 					<img alt="" -->
<!-- 						ng-src="{{activeMenu == 'report' && 'resources/images/icons/icon_report_grey.svg' || 'resources/images/icons/icon_report_red.svg'}}" -->
<!-- 						style="width: 23px;"> -->
					<div>&nbsp;Report</div></a></li>
					<li ng-class="{'active' : activeMenu == 'resources'}" class="dropdown">
					<a
						class="dropdown-toggle" data-toggle="dropdown"
						role="button" aria-expanded="false">
<!-- 					<img alt="" -->
<!-- 						ng-src="{{activeMenu == 'report' && 'resources/images/icons/icon_report_grey.svg' || 'resources/images/icons/icon_report_red.svg'}}" -->
<!-- 						style="width: 23px;"> -->
					
					
					<div>&nbsp;Resources<span class="caret"></span></div></a>
					<ul class="dropdown-menu reportDropDown resourcesHome" role="menu">
							<li style="text-align: center;" ng-class="{'active':activeMenu=='tools'}" ><a href="tools">Tools</a></li>
							<li style="text-align: center;" ng-class="{'active':activeMenu=='gallery'}"><a href="gallery">Gallery</a></li>
						</ul></li>
					<li ng-class="{'active' : activeMenu == 'contactus'}"><a
					href="home#contactus">
<!-- 					<img alt="" -->
<!-- 						ng-src="{{activeMenu == 'contactus' && 'resources/images/icons/icon_report_grey.svg' || 'resources/images/icons/icon_report_red.svg'}}" -->
<!-- 						style="width: 23px;"> -->
					<div>&nbsp;Contact Us</div></a></li>
					<li><a
					href="https://enke.to/x/#YYFB" target="_blank" >
					<div>&nbsp;Data Entry</div></a></li>
					
			</ul>
		</div>
	</div>
</nav>
