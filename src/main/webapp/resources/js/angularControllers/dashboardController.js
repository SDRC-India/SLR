var searDashboard = true; 
function dashboardController($scope, $http, $window, $rootScope, allServices){
		$scope.activeMenu = 'dashboard';
		$scope.selectedGranularitySpider = "";
		$scope.isPushpinClicked = false;
		$scope.lastVisitDataId = 0;
		$scope.isShowTable = false;
		$scope.isShowChart = true;
		$scope.allDistricts = [];
		$scope.sectors = [];
		$scope.pushpinDataCallDone = false;
		$scope.percentileFacility = 0;
		$scope.selectedChartTimePeriod = {timePeriodId: 0};
		// $scope.map = "[]";
		// $scope.map.markers ="[]";
		$scope.getAllTimePeriodsSuccessful = false;
		$scope.getAllDistrictsSuccessful = false;
		$scope.getSectorsSuccessful = false;
		$scope.mapLoadSuccessful = true;
		$scope.subSectors = ["Component", "Service Area"];
		$scope.selectedSubSector = $scope.subSectors[1];
		
		$scope.progressBarUpdateCalled = false;
		$scope.noOfFacilities = 0;
		$scope.hoverwindow = [];
		$scope.noOfFacilitiesPlanned = 0;
		$scope.todayDate = new Date().toLocaleDateString();
		var w = angular.element($window);
		$scope.getWindowDimensions = function() {
			return {
				"h" : w.height(),
				"w" : (w.width() * 90 / 100)
			};
		};
		// this is to make sure that scope gets changes as window get resized.
		w.on("resize", function() {
			$scope.$apply();
		});
		$(".loader").show();
		$scope.pixelOffset = {
			pixelOffset : new google.maps.Size(0, -28)
		};
		
		function convert(array) {
			var map = {};
			for (var i = 0; i < array.length; i++) {
				var obj = array[i];
				if (obj.parentXpathScoreId == -1)
					obj.parentXpathScoreId = null;
				if (!(obj.formXpathScoreId in map)) {
					map[obj.formXpathScoreId] = obj;
					map[obj.formXpathScoreId].children = [];
				}

				if (typeof map[obj.formXpathScoreId].name == 'undefined') {
					map[obj.formXpathScoreId].formXpathScoreId = String(obj.formXpathScoreId);
					map[obj.formXpathScoreId].name = obj.name;
					map[obj.formXpathScoreId].parentXpathScoreId = String(obj.parentXpathScoreId);
					map[obj.formXpathScoreId].score = String(obj.score);
					map[obj.formXpathScoreId].maxScore = String(obj.maxScore);
					map[obj.formXpathScoreId].percentScore = obj.percentScore;
				}

				var parent = obj.parentXpathScoreId || '-';
				if (!(parent in map)) {
					map[parent] = {};
					map[parent].children = [];
				}

				map[parent].children.push(map[obj.formXpathScoreId]);
			}
			return map['-'];
		}
			

		$scope.map = {
			center : {
				latitude : 18.1124372,
				longitude : 79.0192997
			},
			bounds : {},
			clickMarkers : [],
			zoom : 7,
			events : {
				"mouseover" : function(mapModel, eventName, originalEventArgs) {
					for (var i = 0; i < $scope.map.markers.length; i++) {
						if ($scope.map.markers[i].id == originalEventArgs.id) {
							$scope.map.markers[i].showWindow = true;
							break;
						}
					}
					$scope.$apply();
				},
				"mouseout" : function(mapModel, eventName, originalEventArgs) {
					for (var i = 0; i < $scope.map.markers.length; i++) {
						if ($scope.map.markers[i].id == originalEventArgs.id) {
							$scope.map.markers[i].showWindow = false;
							break;
						}
					}
					$scope.$apply();
				},
				"click" : function(mapModel, eventName, originalEventArgs) {
					$(".loader").show();
					$scope.selectedPushpin = '';
					$scope.isPushpinClicked = true;
					$scope.selectedChartTimePeriod = {timePeriodId: 0};
					$scope.selectedPushpin = originalEventArgs.title;
					$scope.lastVisitDataId = originalEventArgs.id;
					$scope.pushpinPhotos = originalEventArgs.images.split(",");
					$scope.beforePhotos=[];
					$scope.afterPhotos=[];
					$scope.methodOfComputation='Percent of secured score against max score of each section for selected facility'
					$scope.beforePhotos= originalEventArgs.beforeImages;
					$scope.afterPhotos=originalEventArgs.afterImages;
					$scope.getSpiderData($scope.lastVisitDataId);
					$('html, body').animate({
						scrollTop : $("#photoGallery").offset().top
					}, 1000);
					$scope.$apply();

				}
			}
		};
		/*
		 * $scope.getChhatisgarhMap = function() {
		 * allServices.getChhatisgarhMap().then(function(data){ $scope.polygons =
		 * JSON.parse(data.selectedRegion); }); };
		 */

		$scope.polygons = [ {
			id : 1,
			path : [{"longitude":77.5979818,"latitude":18.2813508}, {"longitude":77.6030798,"latitude":18.2776216}, {"longitude":77.6012575,"latitude":18.2748493}, {"longitude":77.5988738,"latitude":18.2728766}, {"longitude":77.5979547,"latitude":18.2711132}, {"longitude":77.5971822,"latitude":18.2678419}, {"longitude":77.5950943,"latitude":18.2662955}, {"longitude":77.5933406,"latitude":18.2640749}, {"longitude":77.5907934,"latitude":18.2626276}, {"longitude":77.5868892,"latitude":18.2616561}, {"longitude":77.5836113,"latitude":18.2590984}, {"longitude":77.5786738,"latitude":18.2506358}, {"longitude":77.5757351,"latitude":18.2461248}, {"longitude":77.5736316,"latitude":18.2450541}, {"longitude":77.5725956,"latitude":18.2418048}, {"longitude":77.5737516,"latitude":18.240119}, {"longitude":77.5760104,"latitude":18.2382401}, {"longitude":77.5794059,"latitude":18.2327155}, {"longitude":77.581768,"latitude":18.2317761}, {"longitude":77.5807936,"latitude":18.2281724}, {"longitude":77.5815466,"latitude":18.225438}, {"longitude":77.585636,"latitude":18.2194082}, {"longitude":77.5834382,"latitude":18.2161042}, {"longitude":77.5807198,"latitude":18.2122984}, {"longitude":77.5767485,"latitude":18.2038279}, {"longitude":77.576041,"latitude":18.2014278}, {"longitude":77.5717751,"latitude":18.201842}, {"longitude":77.5674533,"latitude":18.2031708}, {"longitude":77.565627,"latitude":18.2015054}, {"longitude":77.5641395,"latitude":18.1983797}, {"longitude":77.5644735,"latitude":18.1944922}, {"longitude":77.5715383,"latitude":18.1904252}, {"longitude":77.5733916,"latitude":18.1850661}, {"longitude":77.5738129,"latitude":18.1784375}, {"longitude":77.5745854,"latitude":18.1749264}, {"longitude":77.5785105,"latitude":18.1704433}, {"longitude":77.5802434,"latitude":18.1659997}, {"longitude":77.5816294,"latitude":18.162365}, {"longitude":77.5841511,"latitude":18.1595231}, {"longitude":77.5885564,"latitude":18.1573706}, {"longitude":77.5898216,"latitude":18.1571398}, {"longitude":77.5941019,"latitude":18.1554787}, {"longitude":77.5967639,"latitude":18.1563119}, {"longitude":77.5970527,"latitude":18.1520472}, {"longitude":77.5947326,"latitude":18.1277642}, {"longitude":77.5998862,"latitude":18.120824}, {"longitude":77.6023281,"latitude":18.1040616}, {"longitude":77.5982874,"latitude":18.0871114}, {"longitude":77.5916602,"latitude":18.0839522}, {"longitude":77.567207,"latitude":18.0761856}, {"longitude":77.5648107,"latitude":18.0751399}, {"longitude":77.5608021,"latitude":18.0708527}, {"longitude":77.5585488,"latitude":18.0636693}, {"longitude":77.552641,"latitude":18.0638461}, {"longitude":77.5491439,"latitude":18.0647492}, {"longitude":77.5477704,"latitude":18.0634988}, {"longitude":77.5473338,"latitude":18.0559062}, {"longitude":77.550737,"latitude":18.0491616}, {"longitude":77.5533943,"latitude":18.0440522}, {"longitude":77.5540439,"latitude":18.0391111}, {"longitude":77.5537759,"latitude":18.0356746}, {"longitude":77.5562678,"latitude":18.0362056}, {"longitude":77.5601667,"latitude":18.0359408}, {"longitude":77.5637561,"latitude":18.0356172}, {"longitude":77.5740911,"latitude":18.032263}, {"longitude":77.5773092,"latitude":18.0334105}, {"longitude":77.5793397,"latitude":18.0325479}, {"longitude":77.5827552,"latitude":18.0299385}, {"longitude":77.5842119,"latitude":18.0221578}, {"longitude":77.5860643,"latitude":18.0144463}, {"longitude":77.5916539,"latitude":18.0113093}, {"longitude":77.5970551,"latitude":18.0125334}, {"longitude":77.6009868,"latitude":18.0181048}, {"longitude":77.606456,"latitude":18.0182041}, {"longitude":77.6079031,"latitude":18.0099955}, {"longitude":77.6189463,"latitude":18.0072926}, {"longitude":77.6271511,"latitude":17.9965243}, {"longitude":77.6369757,"latitude":17.9959439}, {"longitude":77.6484074,"latitude":17.9993147}, {"longitude":77.652428,"latitude":17.994938}, {"longitude":77.6517076,"latitude":17.9869177}, {"longitude":77.6495566,"latitude":17.9835993}, {"longitude":77.6488271,"latitude":17.9785091}, {"longitude":77.6523736,"latitude":17.9731999}, {"longitude":77.6562111,"latitude":17.9682963}, {"longitude":77.6502147,"latitude":17.9609638}, {"longitude":77.6499279,"latitude":17.9517288}, {"longitude":77.64836,"latitude":17.9465688}, {"longitude":77.6457142,"latitude":17.9479155}, {"longitude":77.6384876,"latitude":17.95099}, {"longitude":77.635407,"latitude":17.9571273}, {"longitude":77.6298649,"latitude":17.9542473}, {"longitude":77.6241009,"latitude":17.9591752}, {"longitude":77.632154,"latitude":17.9633754}, {"longitude":77.6301972,"latitude":17.9465606}, {"longitude":77.6189274,"latitude":17.9396898}, {"longitude":77.6155007,"latitude":17.9349011}, {"longitude":77.6203027,"latitude":17.9294782}, {"longitude":77.6178677,"latitude":17.9123082}, {"longitude":77.6189679,"latitude":17.9043845}, {"longitude":77.6156366,"latitude":17.9019566}, {"longitude":77.6054571,"latitude":17.9074014}, {"longitude":77.5984003,"latitude":17.9030184}, {"longitude":77.5895423,"latitude":17.9091996}, {"longitude":77.5844569,"latitude":17.9031506}, {"longitude":77.5838539,"latitude":17.895361}, {"longitude":77.5872983,"latitude":17.8914984}, {"longitude":77.5942961,"latitude":17.8807926}, {"longitude":77.5992097,"latitude":17.8766419}, {"longitude":77.5990058,"latitude":17.8707429}, {"longitude":77.5957005,"latitude":17.8695532}, {"longitude":77.5869036,"latitude":17.8700267}, {"longitude":77.5863518,"latitude":17.8734658}, {"longitude":77.5817452,"latitude":17.8733041}, {"longitude":77.5788934,"latitude":17.8707388}, {"longitude":77.572692,"latitude":17.8703596}, {"longitude":77.568451,"latitude":17.8642673}, {"longitude":77.567993,"latitude":17.8480623}, {"longitude":77.5583981,"latitude":17.8368551}, {"longitude":77.5553569,"latitude":17.830812}, {"longitude":77.5435389,"latitude":17.8249294}, {"longitude":77.5468256,"latitude":17.8164346}, {"longitude":77.5519967,"latitude":17.8165137}, {"longitude":77.5280636,"latitude":17.8110542}, {"longitude":77.5209587,"latitude":17.8071715}, {"longitude":77.5114062,"latitude":17.8003493}, {"longitude":77.5074752,"latitude":17.7923838}, {"longitude":77.5067653,"latitude":17.7871355}, {"longitude":77.5053216,"latitude":17.7847777}, {"longitude":77.5116926,"latitude":17.7822052}, {"longitude":77.517455,"latitude":17.7823244}, {"longitude":77.5273528,"latitude":17.7800055}, {"longitude":77.5380389,"latitude":17.7767961}, {"longitude":77.5432167,"latitude":17.7732571}, {"longitude":77.5523614,"latitude":17.7695591}, {"longitude":77.5547415,"latitude":17.7686445}, {"longitude":77.556505,"latitude":17.7686904}, {"longitude":77.5584833,"latitude":17.7669752}, {"longitude":77.5603847,"latitude":17.7630168}, {"longitude":77.5642166,"latitude":17.7578976}, {"longitude":77.5652799,"latitude":17.7483509}, {"longitude":77.5642396,"latitude":17.7465138}, {"longitude":77.5625566,"latitude":17.745403}, {"longitude":77.5610656,"latitude":17.7427034}, {"longitude":77.5575962,"latitude":17.7407911}, {"longitude":77.5574781,"latitude":17.7393287}, {"longitude":77.557611,"latitude":17.738246}, {"longitude":77.5568433,"latitude":17.7374445}, {"longitude":77.5550274,"latitude":17.7376413}, {"longitude":77.5543483,"latitude":17.737332}, {"longitude":77.5534182,"latitude":17.7363617}, {"longitude":77.5527391,"latitude":17.7362211}, {"longitude":77.5512506,"latitude":17.7366396}, {"longitude":77.5504829,"latitude":17.7367521}, {"longitude":77.5487999,"latitude":17.7356553}, {"longitude":77.5461353,"latitude":17.7326964}, {"longitude":77.5433585,"latitude":17.7295742}, {"longitude":77.541897,"latitude":17.7297134}, {"longitude":77.5410618,"latitude":17.7287787}, {"longitude":77.539951,"latitude":17.7282258}, {"longitude":77.5381389,"latitude":17.7288782}, {"longitude":77.5372411,"latitude":17.7291963}, {"longitude":77.5361763,"latitude":17.7288583}, {"longitude":77.5354247,"latitude":17.7283213}, {"longitude":77.5332951,"latitude":17.7283412}, {"longitude":77.5326061,"latitude":17.7291566}, {"longitude":77.531604,"latitude":17.7293952}, {"longitude":77.5305392,"latitude":17.7286992}, {"longitude":77.5298977,"latitude":17.7261979}, {"longitude":77.5284363,"latitude":17.7258698}, {"longitude":77.5277264,"latitude":17.7261383}, {"longitude":77.5267813,"latitude":17.7264217}, {"longitude":77.5255342,"latitude":17.725651}, {"longitude":77.5238535,"latitude":17.725303}, {"longitude":77.5222876,"latitude":17.72584}, {"longitude":77.5216508,"latitude":17.7255914}, {"longitude":77.5209514,"latitude":17.7247263}, {"longitude":77.5196256,"latitude":17.7243484}, {"longitude":77.5183729,"latitude":17.7236424}, {"longitude":77.5181433,"latitude":17.7226978}, {"longitude":77.5178092,"latitude":17.7208284}, {"longitude":77.5164939,"latitude":17.7200627}, {"longitude":77.5133675,"latitude":17.7202391}, {"longitude":77.5119502,"latitude":17.7208438}, {"longitude":77.5102229,"latitude":17.7197188}, {"longitude":77.5100753,"latitude":17.7168077}, {"longitude":77.5093076,"latitude":17.7164421}, {"longitude":77.508658,"latitude":17.7171453}, {"longitude":77.507226,"latitude":17.717314}, {"longitude":77.5063199,"latitude":17.7163529}, {"longitude":77.506009,"latitude":17.7160684}, {"longitude":77.5056018,"latitude":17.7158347}, {"longitude":77.5050016,"latitude":17.7157651}, {"longitude":77.5045318,"latitude":17.7159839}, {"longitude":77.5043283,"latitude":17.7163369}, {"longitude":77.5043439,"latitude":17.7166352}, {"longitude":77.5034931,"latitude":17.7172418}, {"longitude":77.5030181,"latitude":17.7173164}, {"longitude":77.5021778,"latitude":17.7171722}, {"longitude":77.5011684,"latitude":17.7171739}, {"longitude":77.4996542,"latitude":17.7165988}, {"longitude":77.4980674,"latitude":17.7160121}, {"longitude":77.4971801,"latitude":17.7163104}, {"longitude":77.4959169,"latitude":17.7162706}, {"longitude":77.493943,"latitude":17.71527}, {"longitude":77.4939706,"latitude":17.7145226}, {"longitude":77.4951257,"latitude":17.7134814}, {"longitude":77.4950371,"latitude":17.7113859}, {"longitude":77.4938229,"latitude":17.7093543}, {"longitude":77.4922728,"latitude":17.7085104}, {"longitude":77.4920218,"latitude":17.7080463}, {"longitude":77.4918447,"latitude":17.7061337}, {"longitude":77.4913132,"latitude":17.7046851}, {"longitude":77.4896154,"latitude":17.7039397}, {"longitude":77.4879324,"latitude":17.703996}, {"longitude":77.4867366,"latitude":17.7045445}, {"longitude":77.4864118,"latitude":17.7049664}, {"longitude":77.486648,"latitude":17.7053039}, {"longitude":77.486589,"latitude":17.7064853}, {"longitude":77.4862347,"latitude":17.7077791}, {"longitude":77.4861904,"latitude":17.7100293}, {"longitude":77.4853636,"latitude":17.7113232}, {"longitude":77.4847936,"latitude":17.7108}, {"longitude":77.4844188,"latitude":17.7101559}, {"longitude":77.4839759,"latitude":17.7087355}, {"longitude":77.4836216,"latitude":17.7072447}, {"longitude":77.4825439,"latitude":17.7065837}, {"longitude":77.4821305,"latitude":17.7057961}, {"longitude":77.4804548,"latitude":17.7043989}, {"longitude":77.4796015,"latitude":17.7044675}, {"longitude":77.4790143,"latitude":17.7047056}, {"longitude":77.4789673,"latitude":17.705168}, {"longitude":77.4786315,"latitude":17.705811}, {"longitude":77.4782933,"latitude":17.7067886}, {"longitude":77.4777016,"latitude":17.7071824}, {"longitude":77.4766055,"latitude":17.7073415}, {"longitude":77.4758643,"latitude":17.7070531}, {"longitude":77.4753319,"latitude":17.706526}, {"longitude":77.4749561,"latitude":17.7056708}, {"longitude":77.4735155,"latitude":17.7053227}, {"longitude":77.4724611,"latitude":17.7055415}, {"longitude":77.4717617,"latitude":17.7052829}, {"longitude":77.4715634,"latitude":17.7049249}, {"longitude":77.4716365,"latitude":17.7045371}, {"longitude":77.4713755,"latitude":17.7039304}, {"longitude":77.470937,"latitude":17.7038012}, {"longitude":77.4705508,"latitude":17.7040398}, {"longitude":77.4702794,"latitude":17.7045868}, {"longitude":77.4698096,"latitude":17.7049349}, {"longitude":77.4689327,"latitude":17.7051536}, {"longitude":77.468365,"latitude":17.7049997}, {"longitude":77.4681427,"latitude":17.7047693}, {"longitude":77.4681113,"latitude":17.7036754}, {"longitude":77.4646664,"latitude":17.7021837}, {"longitude":77.4572836,"latitude":17.6979211}, {"longitude":77.451344,"latitude":17.6904956}, {"longitude":77.45116,"latitude":17.6819621}, {"longitude":77.4518404,"latitude":17.6691364}, {"longitude":77.4491,"latitude":17.6655706}, {"longitude":77.4475646,"latitude":17.658143}, {"longitude":77.4458521,"latitude":17.6562298}, {"longitude":77.4455314,"latitude":17.6496986}, {"longitude":77.4488891,"latitude":17.6443317}, {"longitude":77.4501418,"latitude":17.6332095}, {"longitude":77.4532995,"latitude":17.6312967}, {"longitude":77.44792,"latitude":17.6190366}, {"longitude":77.4481706,"latitude":17.6104401}, {"longitude":77.443227,"latitude":17.6040644}, {"longitude":77.442377,"latitude":17.5920265}, {"longitude":77.439969,"latitude":17.5865559}, {"longitude":77.4416728,"latitude":17.5801349}, {"longitude":77.4610563,"latitude":17.5770988}, {"longitude":77.4739906,"latitude":17.5650565}, {"longitude":77.490047,"latitude":17.5663945}, {"longitude":77.5016433,"latitude":17.5409719}, {"longitude":77.527512,"latitude":17.5730847}, {"longitude":77.5368782,"latitude":17.5713007}, {"longitude":77.5484745,"latitude":17.5494461}, {"longitude":77.5899536,"latitude":17.5628264}, {"longitude":77.5917376,"latitude":17.5324977}, {"longitude":77.6167143,"latitude":17.5293756}, {"longitude":77.6211744,"latitude":17.5222394}, {"longitude":77.6564093,"latitude":17.5213474}, {"longitude":77.6662215,"latitude":17.5097511}, {"longitude":77.6528412,"latitude":17.5030609}, {"longitude":77.6504085,"latitude":17.4922246}, {"longitude":77.644367,"latitude":17.4950327}, {"longitude":77.6479351,"latitude":17.4812063}, {"longitude":77.6403529,"latitude":17.4758542}, {"longitude":77.6109161,"latitude":17.4700561}, {"longitude":77.6104701,"latitude":17.4441874}, {"longitude":77.5957517,"latitude":17.4428494}, {"longitude":77.5730051,"latitude":17.4539996}, {"longitude":77.5725591,"latitude":17.429469}, {"longitude":77.5333101,"latitude":17.4410653}, {"longitude":77.5101175,"latitude":17.427239}, {"longitude":77.5105635,"latitude":17.4125206}, {"longitude":77.5355402,"latitude":17.4040464}, {"longitude":77.5212678,"latitude":17.3862059}, {"longitude":77.530188,"latitude":17.3777317}, {"longitude":77.5208218,"latitude":17.3621213}, {"longitude":77.4824648,"latitude":17.3545391}, {"longitude":77.4722065,"latitude":17.3630133}, {"longitude":77.4793427,"latitude":17.3697035}, {"longitude":77.45169,"latitude":17.3705955}, {"longitude":77.4552581,"latitude":17.3380367}, {"longitude":77.452582,"latitude":17.2867454}, {"longitude":77.4543661,"latitude":17.2760411}, {"longitude":77.4338496,"latitude":17.2880834}, {"longitude":77.4226993,"latitude":17.2532945}, {"longitude":77.4053048,"latitude":17.2372381}, {"longitude":77.4151171,"latitude":17.2301019}, {"longitude":77.3986147,"latitude":17.2086934}, {"longitude":77.3932625,"latitude":17.2069093}, {"longitude":77.3914785,"latitude":17.1921909}, {"longitude":77.370962,"latitude":17.1899609}, {"longitude":77.3598117,"latitude":17.1614162}, {"longitude":77.3754221,"latitude":17.1484818}, {"longitude":77.3763141,"latitude":17.1377775}, {"longitude":77.3990607,"latitude":17.1319794}, {"longitude":77.4035208,"latitude":17.1230592}, {"longitude":77.4235913,"latitude":17.1141389}, {"longitude":77.4387557,"latitude":17.114585}, {"longitude":77.4628403,"latitude":17.1038807}, {"longitude":77.4628074,"latitude":17.103629}, {"longitude":77.4601642,"latitude":17.0927304}, {"longitude":77.4771127,"latitude":17.076228}, {"longitude":77.4994132,"latitude":17.0075422}, {"longitude":77.4784507,"latitude":16.9901478}, {"longitude":77.4793427,"latitude":16.9678472}, {"longitude":77.4641783,"latitude":16.9526828}, {"longitude":77.4677464,"latitude":16.9170019}, {"longitude":77.4534741,"latitude":16.9089737}, {"longitude":77.4522874,"latitude":16.8829968}, {"longitude":77.4592625,"latitude":16.8819521}, {"longitude":77.4591333,"latitude":16.8696259}, {"longitude":77.4601642,"latitude":16.8545603}, {"longitude":77.4627477,"latitude":16.8447211}, {"longitude":77.4580819,"latitude":16.8340915}, {"longitude":77.4588048,"latitude":16.8305377}, {"longitude":77.4556833,"latitude":16.8285249}, {"longitude":77.4565961,"latitude":16.8237855}, {"longitude":77.4613676,"latitude":16.820788}, {"longitude":77.4614005,"latitude":16.8120442}, {"longitude":77.4644234,"latitude":16.8082384}, {"longitude":77.4646952,"latitude":16.8020498}, {"longitude":77.4730151,"latitude":16.8006702}, {"longitude":77.4727805,"latitude":16.7904392}, {"longitude":77.4719906,"latitude":16.7870582}, {"longitude":77.4758009,"latitude":16.7857236}, {"longitude":77.4727805,"latitude":16.7829209}, {"longitude":77.4732452,"latitude":16.7791393}, {"longitude":77.4632756,"latitude":16.7783391}, {"longitude":77.4387653,"latitude":16.785067}, {"longitude":77.4361178,"latitude":16.7795397}, {"longitude":77.4377906,"latitude":16.7649913}, {"longitude":77.4420191,"latitude":16.7620103}, {"longitude":77.4393232,"latitude":16.7504595}, {"longitude":77.4309599,"latitude":16.7358911}, {"longitude":77.4256626,"latitude":16.7261901}, {"longitude":77.4235913,"latitude":16.7145127}, {"longitude":77.4322145,"latitude":16.7146194}, {"longitude":77.4339338,"latitude":16.710703}, {"longitude":77.4394158,"latitude":16.7095573}, {"longitude":77.443058,"latitude":16.7183054}, {"longitude":77.4493816,"latitude":16.7181548}, {"longitude":77.4552434,"latitude":16.7137884}, {"longitude":77.4708746,"latitude":16.7101236}, {"longitude":77.4714065,"latitude":16.7124184}, {"longitude":77.471526,"latitude":16.7065757}, {"longitude":77.4697349,"latitude":16.7023649}, {"longitude":77.4699791,"latitude":16.6914477}, {"longitude":77.4615023,"latitude":16.6721416}, {"longitude":77.4528417,"latitude":16.6766304}, {"longitude":77.4431221,"latitude":16.6739038}, {"longitude":77.4409147,"latitude":16.6685584}, {"longitude":77.4297204,"latitude":16.6685584}, {"longitude":77.4182462,"latitude":16.6655832}, {"longitude":77.4190992,"latitude":16.6579451}, {"longitude":77.4209278,"latitude":16.6570933}, {"longitude":77.4243472,"latitude":16.6537004}, {"longitude":77.4280515,"latitude":16.6526864}, {"longitude":77.4309416,"latitude":16.6521404}, {"longitude":77.438237,"latitude":16.646683}, {"longitude":77.4329575,"latitude":16.6377987}, {"longitude":77.4441078,"latitude":16.6355687}, {"longitude":77.4536558,"latitude":16.6438925}, {"longitude":77.4547505,"latitude":16.6472568}, {"longitude":77.4622219,"latitude":16.651624}, {"longitude":77.4704804,"latitude":16.6488425}, {"longitude":77.4725389,"latitude":16.6449679}, {"longitude":77.4707543,"latitude":16.6437544}, {"longitude":77.4707255,"latitude":16.6415206}, {"longitude":77.4688258,"latitude":16.6382388}, {"longitude":77.4686531,"latitude":16.6365013}, {"longitude":77.4667246,"latitude":16.6344053}, {"longitude":77.4641628,"latitude":16.6322817}, {"longitude":77.4641916,"latitude":16.6291652}, {"longitude":77.4633569,"latitude":16.6274552}, {"longitude":77.457744,"latitude":16.6248075}, {"longitude":77.4551823,"latitude":16.6212496}, {"longitude":77.4535747,"latitude":16.6156448}, {"longitude":77.4581688,"latitude":16.6119648}, {"longitude":77.4575756,"latitude":16.606808}, {"longitude":77.4659199,"latitude":16.6074419}, {"longitude":77.4671575,"latitude":16.601908}, {"longitude":77.4647397,"latitude":16.5972739}, {"longitude":77.4614743,"latitude":16.5953905}, {"longitude":77.4646246,"latitude":16.5929155}, {"longitude":77.4608969,"latitude":16.5826222}, {"longitude":77.4381539,"latitude":16.5825598}, {"longitude":77.420278,"latitude":16.5655556}, {"longitude":77.4155631,"latitude":16.5441363}, {"longitude":77.4017367,"latitude":16.5468124}, {"longitude":77.3914785,"latitude":16.5650989}, {"longitude":77.397821,"latitude":16.5196729}, {"longitude":77.3896797,"latitude":16.5145994}, {"longitude":77.3789902,"latitude":16.5106855}, {"longitude":77.3691779,"latitude":16.5022113}, {"longitude":77.3665018,"latitude":16.4839248}, {"longitude":77.3439257,"latitude":16.4840777}, {"longitude":77.3239255,"latitude":16.4829105}, {"longitude":77.3195018,"latitude":16.4908694}, {"longitude":77.3037278,"latitude":16.4939873}, {"longitude":77.2987576,"latitude":16.4937708}, {"longitude":77.2941346,"latitude":16.4745253}, {"longitude":77.2605635,"latitude":16.4534422}, {"longitude":77.2514309,"latitude":16.4678684}, {"longitude":77.2389426,"latitude":16.4727745}, {"longitude":77.2352117,"latitude":16.4712255}, {"longitude":77.2705727,"latitude":16.4287986}, {"longitude":77.2883825,"latitude":16.4075702}, {"longitude":77.3488501,"latitude":16.3834092}, {"longitude":77.3747221,"latitude":16.3840412}, {"longitude":77.4111665,"latitude":16.3675647}, {"longitude":77.4285617,"latitude":16.3687726}, {"longitude":77.438501,"latitude":16.3801572}, {"longitude":77.4945071,"latitude":16.37599}, {"longitude":77.5163599,"latitude":16.3665216}, {"longitude":77.5526147,"latitude":16.3350395}, {"longitude":77.5738172,"latitude":16.3314501}, {"longitude":77.5846014,"latitude":16.3278208}, {"longitude":77.5917376,"latitude":16.337633}, {"longitude":77.5908456,"latitude":16.3117644}, {"longitude":77.5903996,"latitude":16.2867877}, {"longitude":77.5805873,"latitude":16.2858957}, {"longitude":77.5649769,"latitude":16.297492}, {"longitude":77.5431224,"latitude":16.2872338}, {"longitude":77.5337561,"latitude":16.2627031}, {"longitude":77.5194838,"latitude":16.2707313}, {"longitude":77.5011973,"latitude":16.2676092}, {"longitude":77.4989672,"latitude":16.257797}, {"longitude":77.4875996,"latitude":16.2497018}, {"longitude":77.488709,"latitude":16.2243461}, {"longitude":77.4953991,"latitude":16.2131959}, {"longitude":77.5110095,"latitude":16.21721}, {"longitude":77.5105635,"latitude":16.2234541}, {"longitude":77.4949531,"latitude":16.1873272}, {"longitude":77.4838028,"latitude":16.1583364}, {"longitude":77.4864789,"latitude":16.1159654}, {"longitude":77.4980752,"latitude":16.0972329}, {"longitude":77.5092255,"latitude":16.103477}, {"longitude":77.4927231,"latitude":16.0838525}, {"longitude":77.5029813,"latitude":16.0731483}, {"longitude":77.490939,"latitude":16.0307772}, {"longitude":77.5016433,"latitude":16.0053545}, {"longitude":77.5101175,"latitude":16.0022324}, {"longitude":77.5056574,"latitude":15.9727957}, {"longitude":77.515868,"latitude":15.9575855}, {"longitude":77.5117809,"latitude":15.9282846}, {"longitude":77.5887669,"latitude":15.9060519}, {"longitude":77.606456,"latitude":15.9116921}, {"longitude":77.6394608,"latitude":15.8831474}, {"longitude":77.6590853,"latitude":15.8764572}, {"longitude":77.6925362,"latitude":15.8751192}, {"longitude":77.7161748,"latitude":15.8867155}, {"longitude":77.7588031,"latitude":15.8719218}, {"longitude":77.7991329,"latitude":15.8653069}, {"longitude":77.8339218,"latitude":15.8755652}, {"longitude":77.8499782,"latitude":15.8737812}, {"longitude":77.8669267,"latitude":15.8925136}, {"longitude":77.8878892,"latitude":15.8960817}, {"longitude":77.9284762,"latitude":15.8764572}, {"longitude":77.9730774,"latitude":15.8742272}, {"longitude":78.0020681,"latitude":15.8590628}, {"longitude":78.0167865,"latitude":15.8951897}, {"longitude":78.036411,"latitude":15.9018799}, {"longitude":78.0408711,"latitude":15.8719971}, {"longitude":78.0488993,"latitude":15.8706591}, {"longitude":78.0636177,"latitude":15.8443444}, {"longitude":78.1095569,"latitude":15.827396}, {"longitude":78.1269513,"latitude":15.8349782}, {"longitude":78.1256133,"latitude":15.8488045}, {"longitude":78.1648623,"latitude":15.8488045}, {"longitude":78.1813647,"latitude":15.8876075}, {"longitude":78.1800267,"latitude":15.9005418}, {"longitude":78.2380082,"latitude":15.9290866}, {"longitude":78.2598627,"latitude":15.9946503}, {"longitude":78.2513885,"latitude":16.0258711}, {"longitude":78.2567407,"latitude":16.0312232}, {"longitude":78.3160602,"latitude":16.0289931}, {"longitude":78.3405908,"latitude":16.0526317}, {"longitude":78.4208729,"latitude":16.0829605}, {"longitude":78.4958028,"latitude":16.0731483}, {"longitude":78.5591364,"latitude":16.0561998}, {"longitude":78.6032915,"latitude":16.0963409}, {"longitude":78.6171179,"latitude":16.0990169}, {"longitude":78.6362964,"latitude":16.0914347}, {"longitude":78.6866957,"latitude":16.0405894}, {"longitude":78.7259447,"latitude":16.023195}, {"longitude":78.7540434,"latitude":16.0209649}, {"longitude":78.7750059,"latitude":16.023641}, {"longitude":78.7901703,"latitude":16.0325612}, {"longitude":78.8213911,"latitude":16.061998}, {"longitude":78.8392316,"latitude":16.0941108}, {"longitude":78.8726824,"latitude":16.1101672}, {"longitude":78.8891849,"latitude":16.0985709}, {"longitude":78.9012272,"latitude":16.0976789}, {"longitude":78.9132695,"latitude":16.1088292}, {"longitude":78.9217437,"latitude":16.1217635}, {"longitude":78.9056873,"latitude":16.1650266}, {"longitude":78.9079173,"latitude":16.1908953}, {"longitude":78.8628702,"latitude":16.1529843}, {"longitude":78.856626,"latitude":16.1587825}, {"longitude":78.8378935,"latitude":16.1565524}, {"longitude":78.8303114,"latitude":16.14228}, {"longitude":79.010054,"latitude":16.2404026}, {"longitude":79.0310165,"latitude":16.2457547}, {"longitude":79.0684815,"latitude":16.2381725}, {"longitude":79.1228948,"latitude":16.2377265}, {"longitude":79.1764162,"latitude":16.21721}, {"longitude":79.1920266,"latitude":16.2225621}, {"longitude":79.2250315,"latitude":16.2502148}, {"longitude":79.2277075,"latitude":16.336741}, {"longitude":79.2116511,"latitude":16.3626097}, {"longitude":79.2192333,"latitude":16.4379856}, {"longitude":79.2361818,"latitude":16.4585021}, {"longitude":79.2241394,"latitude":16.491953}, {"longitude":79.2254775,"latitude":16.5080094}, {"longitude":79.2607124,"latitude":16.5463664}, {"longitude":79.2660645,"latitude":16.5615308}, {"longitude":79.3334122,"latitude":16.5784792}, {"longitude":79.3597096,"latitude":16.5796116}, {"longitude":79.3605661,"latitude":16.5796485}, {"longitude":79.4266286,"latitude":16.5824933}, {"longitude":79.4431311,"latitude":16.5936436}, {"longitude":79.4507132,"latitude":16.609254}, {"longitude":79.4970984,"latitude":16.6306626}, {"longitude":79.5541879,"latitude":16.6346767}, {"longitude":79.6001271,"latitude":16.6632214}, {"longitude":79.6478503,"latitude":16.6618834}, {"longitude":79.6688128,"latitude":16.6841839}, {"longitude":79.6830852,"latitude":16.6904281}, {"longitude":79.7227802,"latitude":16.6926581}, {"longitude":79.7504329,"latitude":16.7203108}, {"longitude":79.7749636,"latitude":16.7296771}, {"longitude":79.8101985,"latitude":16.6904281}, {"longitude":79.8619358,"latitude":16.6980103}, {"longitude":79.8730861,"latitude":16.688644}, {"longitude":79.9020768,"latitude":16.6377987}, {"longitude":79.9234854,"latitude":16.6306626}, {"longitude":79.9426639,"latitude":16.6324466}, {"longitude":79.9729926,"latitude":16.6596533}, {"longitude":79.9917251,"latitude":16.6926581}, {"longitude":80.0479226,"latitude":16.7452875}, {"longitude":80.0514906,"latitude":16.7711562}, {"longitude":80.0697771,"latitude":16.8135272}, {"longitude":80.0416784,"latitude":16.8260156}, {"longitude":80.0332042,"latitude":16.8523302}, {"longitude":80.0140257,"latitude":16.8527762}, {"longitude":79.9917251,"latitude":16.8630345}, {"longitude":80.0095656,"latitude":16.9071896}, {"longitude":80.0372183,"latitude":16.9366264}, {"longitude":80.0456925,"latitude":16.9651711}, {"longitude":80.0840495,"latitude":16.9633871}, {"longitude":80.0876176,"latitude":16.9874717}, {"longitude":80.105904,"latitude":16.9812275}, {"longitude":80.1340028,"latitude":16.9852416}, {"longitude":80.1669949,"latitude":17.0261784}, {"longitude":80.182172,"latitude":17.0450072}, {"longitude":80.1942143,"latitude":17.0423311}, {"longitude":80.1955523,"latitude":17.0182465}, {"longitude":80.2013505,"latitude":17.0133404}, {"longitude":80.2178529,"latitude":17.0195845}, {"longitude":80.2356934,"latitude":17.0021901}, {"longitude":80.2500736,"latitude":17.0011802}, {"longitude":80.262008,"latitude":17.0106643}, {"longitude":80.2887687,"latitude":16.9767674}, {"longitude":80.3583465,"latitude":16.9705233}, {"longitude":80.3699428,"latitude":16.9830116}, {"longitude":80.3712808,"latitude":16.9959459}, {"longitude":80.3877833,"latitude":17.0070962}, {"longitude":80.4203421,"latitude":17.0249367}, {"longitude":80.4140979,"latitude":17.0418851}, {"longitude":80.4007176,"latitude":17.0543734}, {"longitude":80.3971495,"latitude":17.0739979}, {"longitude":80.3900133,"latitude":17.076228}, {"longitude":80.3668207,"latitude":17.0566035}, {"longitude":80.4529009,"latitude":17.036979}, {"longitude":80.4716334,"latitude":17.038317}, {"longitude":80.4823377,"latitude":17.0503593}, {"longitude":80.4462108,"latitude":17.0169084}, {"longitude":80.4426427,"latitude":16.9451006}, {"longitude":80.4912579,"latitude":16.9482227}, {"longitude":80.5206947,"latitude":16.9361804}, {"longitude":80.531845,"latitude":16.9500067}, {"longitude":80.5693099,"latitude":16.9268141}, {"longitude":80.5840283,"latitude":16.9263681}, {"longitude":80.5902725,"latitude":16.9116498}, {"longitude":80.5813522,"latitude":16.8969314}, {"longitude":80.5867044,"latitude":16.8737388}, {"longitude":80.5648498,"latitude":16.8688327}, {"longitude":80.5639578,"latitude":16.842518}, {"longitude":80.5545915,"latitude":16.8246775}, {"longitude":80.5559296,"latitude":16.8184334}, {"longitude":80.5858123,"latitude":16.8068371}, {"longitude":80.6040988,"latitude":16.7876586}, {"longitude":80.5626197,"latitude":16.7626819}, {"longitude":80.5108824,"latitude":16.7698181}, {"longitude":80.456469,"latitude":16.7898886}, {"longitude":80.4524549,"latitude":16.8153113}, {"longitude":80.4230182,"latitude":16.8291376}, {"longitude":80.418558,"latitude":16.842518}, {"longitude":80.3998256,"latitude":16.8496542}, {"longitude":80.3989335,"latitude":16.8313677}, {"longitude":80.3730649,"latitude":16.8112972}, {"longitude":80.3587925,"latitude":16.8550063}, {"longitude":80.3351539,"latitude":16.8558983}, {"longitude":80.337384,"latitude":16.8670486}, {"longitude":80.3190975,"latitude":16.8710627}, {"longitude":80.3155294,"latitude":16.9125418}, {"longitude":80.4876898,"latitude":16.9174479}, {"longitude":80.496164,"latitude":17.1078948}, {"longitude":80.5193566,"latitude":17.1083408}, {"longitude":80.5492394,"latitude":17.1235052}, {"longitude":80.5603897,"latitude":17.1382236}, {"longitude":80.5951786,"latitude":17.1194911}, {"longitude":80.6250613,"latitude":17.1016506}, {"longitude":80.6299675,"latitude":17.1101248}, {"longitude":80.6567282,"latitude":17.0878243}, {"longitude":80.6375497,"latitude":17.0717679}, {"longitude":80.6339816,"latitude":17.0588335}, {"longitude":80.6397797,"latitude":17.0552654}, {"longitude":80.6754606,"latitude":17.0610636}, {"longitude":80.6839349,"latitude":17.0686458}, {"longitude":80.7258599,"latitude":17.0646317}, {"longitude":80.8230904,"latitude":17.037871}, {"longitude":80.8324567,"latitude":17.0258287}, {"longitude":80.8587713,"latitude":17.0508053}, {"longitude":80.8565413,"latitude":17.0686458}, {"longitude":80.8641235,"latitude":17.0958525}, {"longitude":80.8552032,"latitude":17.1114629}, {"longitude":80.8667995,"latitude":17.1248432}, {"longitude":80.8703676,"latitude":17.1462518}, {"longitude":80.8962363,"latitude":17.1408996}, {"longitude":80.9131847,"latitude":17.1458058}, {"longitude":80.9149688,"latitude":17.1698904}, {"longitude":80.9051565,"latitude":17.2011112}, {"longitude":80.9305792,"latitude":17.2051253}, {"longitude":80.9560018,"latitude":17.1881768}, {"longitude":80.9912367,"latitude":17.1797026}, {"longitude":81.0260256,"latitude":17.1868388}, {"longitude":81.0465421,"latitude":17.2135995}, {"longitude":81.0621525,"latitude":17.2042332}, {"longitude":81.11835,"latitude":17.2247498}, {"longitude":81.1629511,"latitude":17.232778}, {"longitude":81.1803456,"latitude":17.2546325}, {"longitude":81.179124,"latitude":17.2562032}, {"longitude":81.1709793,"latitude":17.2666748}, {"longitude":81.1705333,"latitude":17.2965576}, {"longitude":81.1816836,"latitude":17.3224263}, {"longitude":81.1901578,"latitude":17.3273324}, {"longitude":81.2673178,"latitude":17.3201962}, {"longitude":81.2891724,"latitude":17.3375907}, {"longitude":81.3226232,"latitude":17.389328}, {"longitude":81.3302054,"latitude":17.3920041}, {"longitude":81.3431397,"latitude":17.3853139}, {"longitude":81.3498299,"latitude":17.3683655}, {"longitude":81.3716845,"latitude":17.3572152}, {"longitude":81.4158396,"latitude":17.3616753}, {"longitude":81.4202997,"latitude":17.3723796}, {"longitude":81.4417083,"latitude":17.3817458}, {"longitude":81.4934456,"latitude":17.4486475}, {"longitude":81.4983517,"latitude":17.4901266}, {"longitude":81.5014738,"latitude":17.5391878}, {"longitude":81.5237744,"latitude":17.5726387}, {"longitude":81.5028118,"latitude":17.5900331}, {"longitude":81.5117321,"latitude":17.6096576}, {"longitude":81.5340326,"latitude":17.6306202}, {"longitude":81.5710516,"latitude":17.6877096}, {"longitude":81.5764037,"latitude":17.7260666}, {"longitude":81.6094086,"latitude":17.7434611}, {"longitude":81.6232349,"latitude":17.7626396}, {"longitude":81.6111926,"latitude":17.8152689}, {"longitude":81.6428594,"latitude":17.8393535}, {"longitude":81.6491036,"latitude":17.8625461}, {"longitude":81.6629299,"latitude":17.8768185}, {"longitude":81.6883526,"latitude":17.8848467}, {"longitude":81.703071,"latitude":17.8612081}, {"longitude":81.7293856,"latitude":17.819283}, {"longitude":81.7530242,"latitude":17.818391}, {"longitude":81.7637285,"latitude":17.8299873}, {"longitude":81.782461,"latitude":17.8317713}, {"longitude":81.7945033,"latitude":17.8442596}, {"longitude":81.7927193,"latitude":17.8527339}, {"longitude":81.7583764,"latitude":17.8933209}, {"longitude":81.703963,"latitude":17.7996585}, {"longitude":81.6847845,"latitude":17.7706678}, {"longitude":81.5688215,"latitude":17.8277572}, {"longitude":81.5541031,"latitude":17.8161609}, {"longitude":81.4809573,"latitude":17.8027806}, {"longitude":81.470253,"latitude":17.8232971}, {"longitude":81.4595487,"latitude":17.8246351}, {"longitude":81.4376942,"latitude":17.8152689}, {"longitude":81.4194077,"latitude":17.820621}, {"longitude":81.4091494,"latitude":17.8050106}, {"longitude":81.3850648,"latitude":17.8067947}, {"longitude":81.2539375,"latitude":17.8121468}, {"longitude":81.1919419,"latitude":17.8469357}, {"longitude":81.159383,"latitude":17.8527339}, {"longitude":81.0889132,"latitude":17.8108088}, {"longitude":81.0737488,"latitude":17.7862782}, {"longitude":81.0559084,"latitude":17.7827101}, {"longitude":81.0322698,"latitude":17.7898463}, {"longitude":81.0041711,"latitude":17.8384615}, {"longitude":80.9943588,"latitude":17.9142834}, {"longitude":80.9845466,"latitude":17.9227577}, {"longitude":80.962246,"latitude":18.0315844}, {"longitude":80.9658141,"latitude":18.0436268}, {"longitude":80.9439595,"latitude":18.0810917}, {"longitude":80.9493117,"latitude":18.1230168}, {"longitude":80.9725042,"latitude":18.1435333}, {"longitude":80.9738423,"latitude":18.1680639}, {"longitude":80.9542178,"latitude":18.1671719}, {"longitude":80.9006964,"latitude":18.1346131}, {"longitude":80.8618934,"latitude":18.132829}, {"longitude":80.8480671,"latitude":18.1975007}, {"longitude":80.8614474,"latitude":18.1988387}, {"longitude":80.8676916,"latitude":18.207313}, {"longitude":80.8592173,"latitude":18.2260454}, {"longitude":80.8297806,"latitude":18.2340736}, {"longitude":80.8172923,"latitude":18.2251534}, {"longitude":80.7883015,"latitude":18.249684}, {"longitude":80.7334421,"latitude":18.2189092}, {"longitude":80.7347802,"latitude":18.171632}, {"longitude":80.7624329,"latitude":18.1631578}, {"longitude":80.7981138,"latitude":18.1662799}, {"longitude":80.8177383,"latitude":18.1872424}, {"longitude":80.7445924,"latitude":18.3023134}, {"longitude":80.7428084,"latitude":18.3219379}, {"longitude":80.7160477,"latitude":18.3830415}, {"longitude":80.7129256,"latitude":18.405788}, {"longitude":80.7267519,"latitude":18.4089101}, {"longitude":80.691071,"latitude":18.4191684}, {"longitude":80.6990992,"latitude":18.4361168}, {"longitude":80.65093,"latitude":18.4722437}, {"longitude":80.6344276,"latitude":18.4985584}, {"longitude":80.6326435,"latitude":18.5186289}, {"longitude":80.6139111,"latitude":18.5262111}, {"longitude":80.5956246,"latitude":18.5489577}, {"longitude":80.531845,"latitude":18.5859767}, {"longitude":80.5140045,"latitude":18.6131834}, {"longitude":80.4885818,"latitude":18.6265637}, {"longitude":80.4502249,"latitude":18.6265637}, {"longitude":80.3815391,"latitude":18.5935588}, {"longitude":80.3668207,"latitude":18.6047091}, {"longitude":80.3485343,"latitude":18.5886527}, {"longitude":80.3364919,"latitude":18.5904368}, {"longitude":80.2963509,"latitude":18.6854372}, {"longitude":80.2673602,"latitude":18.717996}, {"longitude":80.2468436,"latitude":18.7014936}, {"longitude":80.2017965,"latitude":18.6974795}, {"longitude":80.1888622,"latitude":18.675625}, {"longitude":80.1710217,"latitude":18.677409}, {"longitude":80.1580874,"latitude":18.6961415}, {"longitude":80.1108102,"latitude":18.676071}, {"longitude":80.0617489,"latitude":18.716658}, {"longitude":80.0461385,"latitude":18.7211181}, {"longitude":80.0327582,"latitude":18.7465408}, {"longitude":80.0184858,"latitude":18.7510009}, {"longitude":80.0122416,"latitude":18.7666113}, {"longitude":79.9319596,"latitude":18.7844517}, {"longitude":79.9093132,"latitude":18.8395125}, {"longitude":79.9226936,"latitude":18.860921}, {"longitude":79.8776464,"latitude":18.8328223}, {"longitude":79.9244776,"latitude":18.9398651}, {"longitude":79.9151114,"latitude":18.976884}, {"longitude":79.9182334,"latitude":19.0237152}, {"longitude":79.9097592,"latitude":19.0446778}, {"longitude":79.899501,"latitude":19.0491379}, {"longitude":79.8433035,"latitude":19.0366496}, {"longitude":79.8415195,"latitude":19.1062273}, {"longitude":79.8548998,"latitude":19.1245138}, {"longitude":79.9267077,"latitude":19.172683}, {"longitude":79.9061911,"latitude":19.2052419}, {"longitude":79.9039611,"latitude":19.2284345}, {"longitude":79.9289377,"latitude":19.2859699}, {"longitude":79.9454401,"latitude":19.2993503}, {"longitude":79.940534,"latitude":19.3363692}, {"longitude":79.9565904,"latitude":19.4028249}, {"longitude":79.9293837,"latitude":19.442966}, {"longitude":79.9235856,"latitude":19.4666046}, {"longitude":79.8945948,"latitude":19.4942573}, {"longitude":79.8540078,"latitude":19.5067456}, {"longitude":79.8116367,"latitude":19.5388584}, {"longitude":79.8000404,"latitude":19.5709712}, {"longitude":79.7768478,"latitude":19.5821215}, {"longitude":79.7688196,"latitude":19.5955019}, {"longitude":79.7237725,"latitude":19.6053141}, {"longitude":79.6965658,"latitude":19.5852436}, {"longitude":79.6234199,"latitude":19.5767694}, {"longitude":79.5993353,"latitude":19.560713}, {"longitude":79.5814948,"latitude":19.5058536}, {"longitude":79.5663304,"latitude":19.5049616}, {"longitude":79.5360017,"latitude":19.52191}, {"longitude":79.5261894,"latitude":19.5504547}, {"longitude":79.4985367,"latitude":19.5437645}, {"longitude":79.4971987,"latitude":19.5201259}, {"longitude":79.4539356,"latitude":19.4996094}, {"longitude":79.4418932,"latitude":19.521018}, {"longitude":79.4249448,"latitude":19.5352903}, {"longitude":79.3972921,"latitude":19.5352903}, {"longitude":79.354921,"latitude":19.5633891}, {"longitude":79.352691,"latitude":19.5732013}, {"longitude":79.313442,"latitude":19.5758774}, {"longitude":79.2978316,"latitude":19.5674032}, {"longitude":79.2884653,"latitude":19.5919338}, {"longitude":79.2336059,"latitude":19.5794455}, {"longitude":79.2233476,"latitude":19.561605}, {"longitude":79.2202256,"latitude":19.5286002}, {"longitude":79.199263,"latitude":19.5125437}, {"longitude":79.1903428,"latitude":19.4938113}, {"longitude":79.2063992,"latitude":19.4697267}, {"longitude":79.1725023,"latitude":19.4608064}, {"longitude":79.1528778,"latitude":19.4697267}, {"longitude":79.1238871,"latitude":19.5031775}, {"longitude":79.0882062,"latitude":19.5147738}, {"longitude":79.0864221,"latitude":19.5299382}, {"longitude":79.080178,"latitude":19.5326143}, {"longitude":79.0471731,"latitude":19.5330603}, {"longitude":79.0351308,"latitude":19.5473326}, {"longitude":79.002126,"latitude":19.5419805}, {"longitude":78.9762573,"latitude":19.5638351}, {"longitude":78.9619849,"latitude":19.5526848}, {"longitude":78.9383463,"latitude":19.5540228}, {"longitude":78.9329942,"latitude":19.5593749}, {"longitude":78.964215,"latitude":19.5870277}, {"longitude":78.9463745,"latitude":19.6191405}, {"longitude":78.9526187,"latitude":19.6512533}, {"longitude":78.9379003,"latitude":19.6655257}, {"longitude":78.8995433,"latitude":19.6695398}, {"longitude":78.8745667,"latitude":19.6570515}, {"longitude":78.8424539,"latitude":19.6583895}, {"longitude":78.846914,"latitude":19.6989765}, {"longitude":78.8277355,"latitude":19.7609721}, {"longitude":78.8190614,"latitude":19.7607605}, {"longitude":78.7911626,"latitude":19.7600801}, {"longitude":78.768416,"latitude":19.7805966}, {"longitude":78.7220308,"latitude":19.7814886}, {"longitude":78.7070614,"latitude":19.7729348}, {"longitude":78.7001762,"latitude":19.7690003}, {"longitude":78.6796597,"latitude":19.7917469}, {"longitude":78.6190022,"latitude":19.8055733}, {"longitude":78.5873353,"latitude":19.8193996}, {"longitude":78.5686029,"latitude":19.8136015}, {"longitude":78.5097294,"latitude":19.8238597}, {"longitude":78.495903,"latitude":19.8162775}, {"longitude":78.4874288,"latitude":19.794423}, {"longitude":78.4776165,"latitude":19.795315}, {"longitude":78.4597761,"latitude":19.8185076}, {"longitude":78.4120529,"latitude":19.8251978}, {"longitude":78.4086946,"latitude":19.826658}, {"longitude":78.3812781,"latitude":19.8385781}, {"longitude":78.3696818,"latitude":19.8546345}, {"longitude":78.3678977,"latitude":19.8813952}, {"longitude":78.3610439,"latitude":19.8819029}, {"longitude":78.3197285,"latitude":19.8849633}, {"longitude":78.3063481,"latitude":19.8970056}, {"longitude":78.3188365,"latitude":19.91217}, {"longitude":78.3121463,"latitude":19.9179681}, {"longitude":78.2938598,"latitude":19.9148461}, {"longitude":78.2778034,"latitude":19.8515124}, {"longitude":78.3224046,"latitude":19.8412542}, {"longitude":78.3197285,"latitude":19.8207376}, {"longitude":78.3442591,"latitude":19.8078033}, {"longitude":78.3513953,"latitude":19.7832727}, {"longitude":78.3366769,"latitude":19.7395636}, {"longitude":78.3290947,"latitude":19.7364415}, {"longitude":78.3290947,"latitude":19.7154789}, {"longitude":78.3063481,"latitude":19.7056667}, {"longitude":78.299658,"latitude":19.6927324}, {"longitude":78.2693292,"latitude":19.6918403}, {"longitude":78.2532728,"latitude":19.6650797}, {"longitude":78.2711132,"latitude":19.6566054}, {"longitude":78.2947518,"latitude":19.6062061}, {"longitude":78.2947518,"latitude":19.5736473}, {"longitude":78.2800335,"latitude":19.560267}, {"longitude":78.262639,"latitude":19.560267}, {"longitude":78.2786954,"latitude":19.5393044}, {"longitude":78.2956439,"latitude":19.4679426}, {"longitude":78.2809255,"latitude":19.4509942}, {"longitude":78.2568409,"latitude":19.4559003}, {"longitude":78.2354323,"latitude":19.4318157}, {"longitude":78.2077796,"latitude":19.4358298}, {"longitude":78.181911,"latitude":19.4095151}, {"longitude":78.1712067,"latitude":19.3974728}, {"longitude":78.1658545,"latitude":19.4139752}, {"longitude":78.1631785,"latitude":19.3551017}, {"longitude":78.1805729,"latitude":19.3336932}, {"longitude":78.1667466,"latitude":19.2810638}, {"longitude":78.1667466,"latitude":19.2435989}, {"longitude":78.1332957,"latitude":19.2320026}, {"longitude":78.1101031,"latitude":19.248505}, {"longitude":78.0828964,"latitude":19.247613}, {"longitude":78.065948,"latitude":19.2565332}, {"longitude":78.0588118,"latitude":19.248505}, {"longitude":78.0347272,"latitude":19.2435989}, {"longitude":78.029375,"latitude":19.2730356}, {"longitude":78.0110886,"latitude":19.2806178}, {"longitude":78.0017223,"latitude":19.2984583}, {"longitude":77.9691635,"latitude":19.3109466}, {"longitude":77.9464169,"latitude":19.3336932}, {"longitude":77.9241164,"latitude":19.3443974}, {"longitude":77.8960176,"latitude":19.3198668}, {"longitude":77.8768391,"latitude":19.3229889}, {"longitude":77.871487,"latitude":19.3145147}, {"longitude":77.8442803,"latitude":19.3042564}, {"longitude":77.8456183,"latitude":19.2859699}, {"longitude":77.8509705,"latitude":19.2574252}, {"longitude":77.8683649,"latitude":19.2623313}, {"longitude":77.8839753,"latitude":19.2583172}, {"longitude":77.8893275,"latitude":19.2690215}, {"longitude":77.8844213,"latitude":19.2792798}, {"longitude":77.8451723,"latitude":19.2284345}, {"longitude":77.8300079,"latitude":19.1989977}, {"longitude":77.8282239,"latitude":19.1900775}, {"longitude":77.8420502,"latitude":19.1829413}, {"longitude":77.832684,"latitude":19.1570726}, {"longitude":77.8148436,"latitude":19.1374481}, {"longitude":77.8286699,"latitude":19.1160396}, {"longitude":77.831792,"latitude":19.091955}, {"longitude":77.8121675,"latitude":19.0870488}, {"longitude":77.7987871,"latitude":19.1057813}, {"longitude":77.7894209,"latitude":19.1053353}, {"longitude":77.7769326,"latitude":19.0732225}, {"longitude":77.7809467,"latitude":19.053152}, {"longitude":77.7430357,"latitude":19.0611802}, {"longitude":77.7408057,"latitude":19.0304054}, {"longitude":77.7488339,"latitude":19.0174711}, {"longitude":77.7510639,"latitude":18.9835742}, {"longitude":77.7648903,"latitude":18.9782221}, {"longitude":77.7706884,"latitude":18.9862503}, {"longitude":77.8005712,"latitude":18.9844662}, {"longitude":77.7961111,"latitude":18.9514614}, {"longitude":77.8108294,"latitude":18.9478933}, {"longitude":77.8371441,"latitude":18.9541374}, {"longitude":77.8242098,"latitude":18.9131044}, {"longitude":77.8402662,"latitude":18.9081983}, {"longitude":77.8652428,"latitude":18.9104283}, {"longitude":77.871487,"latitude":18.8872357}, {"longitude":77.8821913,"latitude":18.8858977}, {"longitude":77.8826373,"latitude":18.8698413}, {"longitude":77.908952,"latitude":18.8613671}, {"longitude":77.9134121,"latitude":18.8484327}, {"longitude":77.9276844,"latitude":18.8444186}, {"longitude":77.9075137,"latitude":18.8294989}, {"longitude":77.9427486,"latitude":18.8223627}, {"longitude":77.8374899,"latitude":18.8076443}, {"longitude":77.764344,"latitude":18.7077378}, {"longitude":77.7576539,"latitude":18.7072918}, {"longitude":77.7496257,"latitude":18.6898973}, {"longitude":77.7692502,"latitude":18.6907893}, {"longitude":77.7866446,"latitude":18.6840992}, {"longitude":77.7326772,"latitude":18.676071}, {"longitude":77.7295551,"latitude":18.6426201}, {"longitude":77.7482876,"latitude":18.6051551}, {"longitude":77.7366913,"latitude":18.5547559}, {"longitude":77.7121607,"latitude":18.5543098}, {"longitude":77.6760338,"latitude":18.5413755}, {"longitude":77.6577473,"latitude":18.5271031}, {"longitude":77.645259,"latitude":18.5364694}, {"longitude":77.6345547,"latitude":18.5453896}, {"longitude":77.6292026,"latitude":18.5507418}, {"longitude":77.6403529,"latitude":18.5538638}, {"longitude":77.6488271,"latitude":18.5525258}, {"longitude":77.6162682,"latitude":18.5485117}],
			stroke : {
				color : '#f75b46',
				weight : 2,
			},
			editable : true,
			draggable : false,
			geodesic : false,
			visible : true,
			fill : {
				color : 'rgb(242,230,223)',
				opacity : 0.7
			}
		} ];
		$scope.map.markers = [];
		$scope.tryPushpinData = function(){
			if($scope.mapLoadSuccessful && $scope.getAllTimePeriodsSuccessful && $scope.getAllDistrictsSuccessful
					 && $scope.getSectorsSuccessful){
				$scope.getPushpinData();
				$scope.getSpiderData();
			}
		}; 
		$scope.getAllTimePeriods = function() {
			allServices.getAllTimeperiods().then(function(data) {
				$scope.allTimePeriods = data;
				
				//check for all district successful(used for 1st load and get pushpin)
				$scope.getAllTimePeriodsSuccessful = true;
				// console.log($scope.allTimePeriods);
				$scope.selectTimePeriod($scope.allTimePeriods[$scope.allTimePeriods.length-1]);
//				$scope.selectChartTimePeriod($scope.allTimePeriods[$scope.allTimePeriods.length-1]);
				/*this function checks whether all districts name and sectors/timeperiod received
				if yes it calls for pushpin in map*/
				$scope.tryPushpinData();
//				$scope.getAllTimePeriodsSuccessful = true;
//				$scope.checkBasicDataSuccessful();
			});
		};
		$scope.getAllDistricts = function(stateId) {
			allServices.getAllDistricts(stateId).then(function(data) {
				$scope.allDistricts = data;
				$scope.selectDistrict($scope.allDistricts[0]);
				
				//check for all district successful(used for 1st load and get pushpin)
				$scope.getAllDistrictsSuccessful = true; 
				/*this function checks whether all districts name and sectors/timeperiod received
				if yes it calls for pushpin in map*/
				$scope.tryPushpinData();
				/*$scope.checkBasicDataSuccessful();*/
			});
		};
		$scope.getSectors = function() {
			allServices.getSectors().then(function(data) {
				$scope.sectors = data;
				$scope.selectSector($scope.sectors[0]);
				$scope.getAllDistricts(3);
				
				//check for all district successful(used for 1st load and get pushpin)
				$scope.getSectorsSuccessful = true;
				
				/*this function checks whether all districts name and sectors/timeperiod received
				if yes it calls for pushpin in map*/
				$scope.tryPushpinData();
			});
		};
		
		//this function used to get markers/pushpins in the map
		$scope.getPushpinData = function() {
				$scope.map.markers = [];
				$(".loader").show();
				allServices.getGoogleMapData(3, $scope.selectedDistrict.areaId, $scope.selectedIndicator.formXpathScoreMappingId, 
						$scope.selectedIndicator.label, $scope.selectedSector.sectorId, $scope.selectedTimePeriod.timePeriodId).then(function(data) {
					$(".loader").fadeOut();
//					checkSessionOut(data);
//					$scope.noOfFacilities = data.length;
//					$scope.tempNoOfFacilities = JSON.parse(JSON.stringify($scope.noOfFacilities));
					
					$scope.map.markers = data;
					$scope.markersArray = $scope.map.markers;
					if($scope.map.markers.length == 0){
						$scope.errorMsg = "No data available for this selection";
						$("#errorMessage").modal("show");
					}
					$scope.pushpinPhotos = [];
					$scope.beforePhotos= [];
					$scope.afterPhotos=[];
					$scope.methodOfComputation='Average of percent of secured score against max score of each section for all the facilities avialable within a selected district/state'
					$scope.greenMarkers = 0;
					$scope.redMarkers = 0;
					$scope.orangeMarkers = 0;
					$scope.noOfFacilitiesSuccessful = true;
					if($scope.noOfFacilitiesPlannedSuccessful){
						$scope.progressBarUpdate();
						$scope.noOfFacilitiesPlannedSuccessful = false;	
						$scope.noOfFacilitiesSuccessful = false;
					}
					for (var i = 0; i < $scope.map.markers.length; i++) {
						if (parseFloat($scope.map.markers[i].dataValue) >= 80)
							$scope.greenMarkers++;
						else if (parseFloat($scope.map.markers[i].dataValue) < 60)
							$scope.redMarkers++;
						else
							$scope.orangeMarkers++;
					}
					$scope.$apply();
				});
				
		};
		$("#searchDashboard").autocomplete({
			source : $scope.optArrayDashboard,
			appendTo : "#searchDivDashboard",
			select: function(event, ui) {
				document.getElementById('searchDashboard').value = ui.item.value;
				$scope.searchNodeDashboard();
				$scope.$apply();
		    }
		});
		$scope.searchNodeDashboard= function() {
			$scope.optArrayDashboard = [];
			$scope.nooptArrayDashboard = [];
			var selectedVal = document.getElementById('searchDashboard').value;
			var node = [];
			var colorChange = [];
			if (searDashboard == true) {
				$scope.optArrayDashboard = [];
				for(var i = 0; i<$scope.markersArray.length; i++){
					node.push($scope.markersArray[i].title);
					colorChange.push($scope.markersArray[i].id);
				}
				
				if (selectedVal == "") {
					for (var i = 0; i < $scope.map.markers.length; i++) {
//						var iconPath = $scope.map.markers[i].path;
						var icon = $scope.map.markers[i].icon;
						var replacedIcon = icon.replace(".png", "").replace("-faded", "");
						$scope.map.markers[i].icon = replacedIcon+".png";
					}
				} else {
					for(var i = 0; i<$scope.markersArray.length; i++){
						if (node[i].toUpperCase().match(selectedVal.toUpperCase())) {
							$scope.optArrayDashboard.push(node[i]);
						}else{
							$scope.nooptArrayDashboard.push(node[i]);
						}
					}
					
					$("#searchDashboard").autocomplete({
						source : $scope.optArrayDashboard,
						appendTo : "#searchDivDashboard",
						select: function(event, ui) {
							document.getElementById('searchDashboard').value = ui.item.value;
							$scope.searchNodeDashboard();
							$scope.$apply();
					    }
					});
					
					for(var j=0;j<$scope.optArrayDashboard.length;j++){
						for (var i = 0; i < $scope.map.markers.length; i++) {
							if ($scope.map.markers[i].title == $scope.optArrayDashboard[j]) {
//								var iconPath = $scope.map.markers[i].path;
								var icon = $scope.map.markers[i].icon;
								var replacedIcon = icon.replace(".png", "").replace("-faded", "");
								$scope.map.markers[i].icon = replacedIcon+".png";
							}
						}
					}
					for(var j=0;j<$scope.nooptArrayDashboard.length;j++){
						for (var i = 0; i < $scope.map.markers.length; i++) {
							if ($scope.map.markers[i].title == $scope.nooptArrayDashboard[j]) {
//								var iconPath = $scope.map.markers[i].path;
								var icon = $scope.map.markers[i].icon;
								var replacedIcon = icon.replace(".png", "").replace("-faded", "");
								$scope.map.markers[i].icon = replacedIcon+"-faded.png";
							}
						}
					}
		//
				}
			}
		};
		$scope.getSpiderData = function() {
			allServices.getSpiderData(3, $scope.selectedDistrict.areaId, $scope.selectedSector.sectorId, $scope.lastVisitDataId,
					$scope.selectedTimePeriod.timePeriodId, $scope.selectedChartTimePeriod.timePeriodId).then(function(data) {
					$scope.wholeSpiderData = data;	
				$scope.spiderdata = $scope.wholeSpiderData[$scope.selectedSubSector];
				if ($scope.spiderdata && $scope.spiderdata.dataCollection) {
					var d = $scope.spiderdata.dataCollection;
					watchSpiderData(d);
				}
				$(".loader").fadeOut();
			});
		};
		$scope.getSectors();
		$scope.getAllTimePeriods();
		
		$scope.selectSubSector = function(subSector){
			$scope.selectedSubSector = subSector;
			$scope.pushpinFilterWord = "";
			$scope.getSpiderData($scope.lastVisitDataId);
			
		};
		
		$scope.selectSector = function(sector) {
			$scope.selectedChartTimePeriod = {timePeriodId: 0};
			$scope.selectedSector = sector;
			$scope.selectIndicator($scope.selectedSector.formXpathModel[0]);
			$scope.lastVisitDataId = 0;
			$scope.selectedPushpin = '';
			$scope.pushpinFilterWord = "";
			/*if ($scope.allDistricts.length && !$scope.pushpinDataCallDone) {
				$scope.getPushpinData($scope.selectedParentSector.formId,
						$scope.selectedSector.formXpathScoreId,
						$scope.selectedDistrict.areaId);
				$scope.pushpinDataCallDone = true;
			}
*/
		};
		$scope.selectIndicator = function(indicator) {
			$scope.selectedIndicator = indicator;
			$scope.lastVisitDataId = 0;
			$scope.selectedPushpin = '';
			$scope.pushpinFilterWord = "";
		};
		$scope.selectDistrict = function(District) {
//			$scope.selectedPushpin = "";
//			$scope.lastVisiDataId = 0;
			$scope.selectedChartTimePeriod = {timePeriodId: 0};
			$scope.selectedDistrict = District;
			$scope.lastVisitDataId = 0;
			$scope.selectedPushpin = '';
			$scope.pushpinFilterWord = "";
//			if ($scope.sectors.length && !$scope.pushpinDataCallDone) {
//				$scope.getPushpinData($scope.selectedParentSector.formId,
//						$scope.selectedSector.formXpathScoreId,
//						$scope.selectedDistrict.areaId);
//				$scope.pushpinDataCallDone = true;
//			}
//			$scope.getSpiderData($scope.selectedParentSector.formId, 0,
//					$scope.selectedDistrict.areaId);
//			$scope.getPlannedFacilities($scope.selectedParentSector.formId, $scope.selectedTimePeriod.timePeriod_Nid,
//					$scope.selectedDistrict.areaId);
		};
		$scope.selectTimePeriod = function(timeperiod) {
			$scope.selectedTimePeriod = timeperiod;
			$scope.lastVisitDataId = 0;
			$scope.selectedPushpin = '';
			$scope.pushpinFilterWord = "";
			$scope.selectedChartTimePeriod = {timePeriodId: 0};
		};
		$scope.selectChartTimePeriod = function(timeperiod) {
			$scope.selectedChartTimePeriod = timeperiod;
		};
		$scope.compareChartTimeperiod = function(item){
			 return item != $scope.selectedTimePeriod;
			};
		/*$scope.mapExport = function() {		            //URL of Google Static Maps.
		            var staticMapUrl = "https://maps.googleapis.com/maps/api/staticmap";

		            //Set the Google Map Center.
		            staticMapUrl += "?center=" + $scope.map.center.latitude + "," + $scope.map.center.longitude;

		            //Set the Google Map Size.
		            staticMapUrl += "&size=440x700";

		            //Set the Google Map Zoom.
		            staticMapUrl += "&zoom=" + $scope.map.zoom;

		            //Set the Google Map Type.
//		            staticMapUrl += "&maptype=" + $scope.map.mapTypeId;

		            //Loop and add Markers.
		            for (var i = 0; i < $scope.map.markers.length; i++) {
		                staticMapUrl += "&markers=color:red|" + $scope.map.markers[i].latitude + "," + $scope.map.markers[i].longitude;
		            }

		            //Display the Image of Google Map.
		            window.open(staticMapUrl);
		        };	*/
	
}
	$(document).ready(function() {
		$(".dist-list ul.dropdown-menu input").click(function(e) {
			e.preventDefault();
		});
		
		$('.pop').on('click', function() {
			$('.imagepreview').attr('src', $(this).find('img').attr('src'));
			$('#imagemodal').modal('show');   
		});	
		
	});
	