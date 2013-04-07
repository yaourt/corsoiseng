(function () {  // anonymous wrapper
    "use strict";

    var corsoiseng = angular.module('corsoiseng', [ ]);
    
    corsoiseng.controller('CorsoiseNGCtrl', ['$scope', '$http', CorsoiseNGCtrl]);

    function CorsoiseNGCtrl($scope, $http) {
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
            $http.get('/incCount');
        	updateData();
        }
        $scope.incCount = incCount;
        
        function decCount() {
            $http.get('/decCount');
        	updateData();
        }
        $scope.decCount = decCount;
        
        function incPietra() {
            $http.get('/incPietra');
        	updateData();
        }
        $scope.incPietra = incPietra;
        
        function decPietra() {
            $http.get('/decPietra');
        	updateData();
        }
        $scope.decPietra = decPietra;
        
        function incTerrine() {
            $http.get('/incTerrine');
        	updateData();
        }
        $scope.incTerrine = incTerrine;
        
        function decTerrine() {
            $http.get('/decTerrine');
        	updateData();
        }
        $scope.decTerrine = decTerrine;
    }
})();