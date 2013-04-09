<div class="row-fluid" ng-controller="CorsoiseNGCtrl">
  <div class="span8 offset2">
	<table class="table table-striped table-bordered" >
		<thead>
			<tr>
				<th colspan="2"></th>
				<th style="width: 25%">Kombien Kil Sera ?</th>
				<th style="width: 25%">Kiki veut la Pietra ?</th>
				<th style="width: 25%">Kiki veut la Terrine ?</th>
			</tr>
		</thead>
		<tbody>
			<tr class="warning">
				<th colspan="2">Total</th>
				<th><p class="text-right">{{corsoisengData.sum.count}}</p></th>
				<th><p class="text-right">{{corsoisengData.sum.pietra}}</p></th>
				<th><p class="text-right">{{corsoisengData.sum.terrine}}</p></th>
			</tr>
			<tr class="info">
				<td class="vcenter"><a class="btn btn-mini" href="#" title="Admin headshot !"><i class="icon-trash icon-white"></i></a>&nbsp;<a class="btn btn-mini" title="Cpa moi, m'a trompé" ng-click="deleteMe()"><i class="icon-minus icon-white"></i></a></td>
				<td class="vcenter">Moi<br /><span ng-bind-html-unsafe="corsoisengData.me.pseudo"</span></td>
				<td><p class="text-right">{{corsoisengData.me.count}}</p><p class="text-center"><a class="btn" ng-click="incCount()">+1</a>&nbsp;<a class="btn" ng-click="decCount()">-1</a></p></td>
				<td><p class="text-right">{{corsoisengData.me.pietra}}</p><p class="text-center"><a class="btn" ng-click="incPietra()">+1</a>&nbsp;<a class="btn" ng-click="decPietra()">-1</a></p></td>
				<td><p class="text-right">{{corsoisengData.me.terrine}}</p><p class="text-center"><a class="btn" ng-click="incTerrine()">+1</a>&nbsp;<a class="btn" ng-click="decTerrine()">-1</a></p></td>
			</tr>
			<tr ng-repeat="other in corsoisengData.others">
				<td class="vcenter"><a class="btn btn-mini" href="#"><i class="icon-trash icon-white"></i> </a></td>
				<td class="vcenter"><span ng-bind-html-unsafe="other.pseudo"</span></td>
				<td><p class="text-right">{{other.count}}</p></td>
				<td><p class="text-right">{{other.pietra}}</p></td>
				<td><p class="text-right">{{other.terrine}}</p></td>
			</tr>
		</tbody>
	</table>
  </div>
</div>