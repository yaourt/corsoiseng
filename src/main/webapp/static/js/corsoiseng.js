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
    }
})();