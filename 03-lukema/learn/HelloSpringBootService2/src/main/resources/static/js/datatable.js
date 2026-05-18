/**
 * https://datatables.net/reference/api/
 */

const dataSet = [{
  'name': 'Tiger Nixon',
  'position': 'System Architect',
  'salary': '$3,120',
  'start_date': '2011/04/25',
  'office': 'Edinburgh',
  'extn': '5421'
}, {
  'name': 'Garrett Winters',
  'position': 'Director',
  'salary': '$5,300',
  'start_date': '2011/07/25',
  'office': 'Edinburgh',
  'extn': '8422'
}];

$(function () {

  $('#table1').DataTable({
    data: dataSet,
    columns: [
      { data: 'name' },
      { data: 'position' },
      { data: 'salary' },
      { data: 'office' }
    ]
  });

  $('#table1 tbody').on('click', 'tr', function () {
    console.log($('#table1').DataTable().row(this).data());
    // $('#table1').DataTable().row(this).remove().draw();
  });

  $('#table1 tbody').on('click', 'td', function () {
    let cell = $('#table1').DataTable().cell(this);
    cell.data(cell.data() + ' ' + 1).draw();
    // note - call draw() to update the table's draw state with the new data
  });

  $('#table2').DataTable(
    {
      data: [
        new Employee('Tiger Nixon', 'System Architect', '$3,120', 'Edinburgh'),
        new Employee('Garrett Winters', 'Director', '$5,300', 'Edinburgh')
      ],
      columns: [
        { data: 'name' },
        { data: 'position' },
        { data: 'salary' },
        { data: 'office' }
      ]
    });

  $('#table3').DataTable({
    data: [
      {
        'column1': '<input type="text" value="0" size="10"/>',
        'column2': 'row 1 column 2'
      },
      {
        'column1': 'row 2 column 1',
        'column2': 'row 2 column 2'
      }
    ],
    columns: [
      { data: 'column1' },
      { data: 'column2' }
    ]
  });

});

function Employee(name, position, salary, office) {
  this.name = name;
  this.position = position;
  this.salary = salary;
  this._office = office;

  this.office = function () {
    return this._office;
  };
}

function addEmployeeToTable1() {

  console.log('Clicked.');

  $('#table1').DataTable().row.add(
    {
      'name': 'Luke Ma',
      'position': 'Developer',
      'salary': '$15,300',
      'start_date': '2011/07/25',
      'office': 'One Metro Center',
      'extn': '8422'
    }
  );

  /*
  $('#table1').DataTable().rows.add(
    [{
      'name': 'Luke Ma',
      'position': 'Developer',
      'salary': '$15,300',
      'start_date': '2011/07/25',
      'office': 'One Metro Center',
      'extn': '8422'
    }]
  );
  */

  $('#table1').DataTable().rows().invalidate().draw();


  console.log('dataSet.length = ' + dataSet.length);
  console.log('data.length = ' + $('#table1').DataTable().data().toArray().length);
  // console.log(JSON.stringify($('#table1').DataTable(), undefined, 2));
  // console.log(JSON.stringify($('#table1').DataTable().data(), undefined, 2));
  // console.log(JSON.stringify($('#table1').DataTable().data().toArray(), undefined, 2));

  // $('#table1').DataTable().rows().invalidate().draw();
  // $('#table1').DataTable().clear();
  // $('#table1').DataTable().draw(dataSet);

  /**
   * data:
   */
  const data = $('#table1').DataTable().data().toArray();
  data.forEach(function (row, i) {
    console.log('row ' + i + JSON.stringify(row));
  });
}
