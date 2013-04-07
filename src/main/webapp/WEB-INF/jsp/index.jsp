<div class="row-fluid">
	<table class="table table-striped table-bordered span6 offset3" >
		<thead>
			<tr>
				<th colspan="2"></th>
				<th>Kombien Kil Sera ?</th>
				<th>Kiki veut la Pietra ?</th>
				<th>Kiki veut la Terrine ?</th>
			</tr>
			<tr class="warning">
				<th colspan="2">Total</th>
				<th>{{sum.count}}</th>
				<th>{{sum.pietra}}</th>
				<th>{{sum.terrine}}</th>
			</tr>
			
		</thead>
		<tbody>
			<tr class="info">
				<td colspan="2" rowspan="2">Moi<br />({{me.pseudo}})</td>
				<td>{{me.count}}</td>
				<td>{{me.pietra}}</td>
				<td>{{me.terrine}}</td>
			</tr>
			<tr class="info">
				<td><p class="text-center"><a class="btn" href="/incCount">+1</a>&nbsp;<a class="btn" href="/decCount">-1</a></p></td>
				<td><p class="text-center"><a class="btn" href="/incPietra">+1</a>&nbsp;<a class="btn" href="/decPietra">-1</a></p></td>
				<td><p class="text-center"><a class="btn" href="/incTerrine">+1</a>&nbsp;<a class="btn" href="/decTerrine">-1</a></p></td>
			</tr>
			<tr ng-repeat="other int others">
				<td colspan="2">{{other.pseudo}}</td>
				<td>{{other.count}}</td>
				<td>{{other.pietra}}</td>
				<td>{{other.terrine}}</td>
			</tr>
		</tbody>
	</table>
</div>