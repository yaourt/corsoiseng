(function () {  // anonymous wrapper
    "use strict";

    var corsoiseng = angular.module('corsoiseng', [ ]);
    
    corsoiseng.controller('CorsoiseNGCtrl', ['$scope', '$http', '$timeout', '$log', CorsoiseNGCtrl]);

    function CorsoiseNGCtrl($scope, $http, $timeout, $log) {
        function updateData() {
            $http
            .get('/data.json')
            .success(
                function(data) {
                    $scope.corsoisengData = data;
                }
            );        	
        }
        $scope.updateData = updateData;
        updateData();
        
        function incCount() {
            $http.get('/incCount').success(function(){updateData();});
        }
        $scope.incCount = incCount;
        
        function decCount() {
            $http.get('/decCount').success(function(){updateData();});
        }
        $scope.decCount = decCount;
        
        function incPietra() {
            $http.get('/incPietra').success(function(){updateData();});
        }
        $scope.incPietra = incPietra;
        
        function decPietra() {
            $http.get('/decPietra').success(function(){updateData();});
        }
        $scope.decPietra = decPietra;
        
        function incTerrine() {
            $http.get('/incTerrine').success(function(){updateData();});
        }
        $scope.incTerrine = incTerrine;
        
        function decTerrine() {
            $http.get('/decTerrine').success(function(){updateData();});
        }
        $scope.decTerrine = decTerrine;

        function deleteMe() {
            $http.get('/deleteMe').success(function(){updateData();});
        }
        $scope.deleteMe = deleteMe;
        
        // Ping
        function randomLimitedInt() {
        	var val = 0;
        	while(val < 15 || val > 45) {
        		val = Math.floor((Math.random()*100)+1);
        	}
        	return val;
        }
        var ping = function() {
        	var randomTO = randomLimitedInt();
        	$log.info("Ping ongoing, next ping in " + randomTO + " minutes");
        	pinger = $timeout(ping, 1000 * 60 * randomTO);
        	$http.get('/ping').success(function(){$log.info("Ping done")});
        };
        
        var pinger = $timeout(ping, 1000 * 60 * randomLimitedInt());

    }
})();