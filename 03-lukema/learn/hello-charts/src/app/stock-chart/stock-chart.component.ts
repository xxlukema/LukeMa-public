import { formatNumber } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Chart, ChartConfiguration, ChartType, ChartTypeRegistry, TooltipItem } from 'chart.js';
import ChartDataLabels, { Context } from 'chartjs-plugin-datalabels';
import { BaseChartDirective } from 'ng2-charts';
import { Subscription } from 'rxjs';
import { StockChartService } from './stock-chart.service';


@Component({
  selector: 'app-stock-chart',
  templateUrl: './stock-chart.component.html',
  styleUrls: ['./stock-chart.component.scss']
})
export class StockChartComponent implements OnInit, OnDestroy {

  constructor(private stockChartService: StockChartService) {
    Chart.register(ChartDataLabels);
  }

  /**
   * [`ng2-charts` Setting Color and Label]<https://stackoverflow.com/questions/42095640/ng2-charts-setting-colors-not-working>
   */

  tickerSymbolPattern = {
    B: {
      pattern: /[a-zA-Z0-9.]/,
      optional: true,
      symbol: 'X'
    }
  };

  symbol?: string;
  channel$?: Subscription;

  stock: any;

  /**
   * TODO: Chart data start here.
   */

  public lineChartLabels: string[] = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];

  public lineChartData: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [65, 59, 80, 81, 56, 55, 40],
        label: 'Series A',
        backgroundColor: 'rgba(255,0,0,0.3)',
        borderColor: 'blue',
        pointBackgroundColor: 'rgba(148,159,177,1)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(148,159,177,0.8)'
      },
      {
        data: [28, 48, 40, 19, 86, 27, 90],
        label: 'Series B',
        backgroundColor: 'rgba(255,0,0,0.3)',
        borderColor: 'yellow',
        borderDash: [20, 6],
        pointBackgroundColor: 'rgba(148,159,177,1)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(148,159,177,0.8)'
      },
      {
        data: [180, 480, 770, 90, 1000, 270, 400],
        label: 'Series C',
        yAxisID: 'y-axis-0',
        backgroundColor: 'lime',
        borderColor: 'red',
        borderDash: [30, 6],
        pointRadius: 5,
        pointHoverRadius: 6,
        pointHoverBorderWidth: 2,
        pointHitRadius: 8,
        pointBackgroundColor: 'lime',
        pointBorderColor: 'green',
        pointHoverBackgroundColor: 'lightblue',
        pointHoverBorderColor: 'gold'
        // fill: 'origin'
      }
    ],
    labels: this.lineChartLabels
  };

  public lineChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    plugins: {
      title: {
        display: true,
        color: 'blue',
        text: 'Line Chart'
      },
      legend: {
        display: true,
        position: 'top',
      },
      datalabels: {
        display: false
      }
    },
    scales: {
      // We use this empty structure as a placeholder for dynamic theming.
      x: {
        title: {
          display: true,
          text: 'Months',
          color: 'blue'
        }
      },
      y: {
        position: 'right',
        beginAtZero: true,
        grid: {
          color: 'rgba(255,0,0,0.3)'
        },
        ticks: {
          color: 'blue'
        },
        title: {
          display: true,
          text: 'Y - Values',
          color: 'gold'
        }
      }
    }
    /*
    annotation: {
      annotations: [
        {
          type: 'line',
          mode: 'vertical',
          scaleID: 'x-axis-0',
          value: 'March',
          borderColor: 'orange',
          borderWidth: 2,
          label: {
            enabled: true,
            fontColor: 'orange',
            content: 'LineAnno'
          }
        }
      ]
    }
    */
  };

  /*
  public lineChartColors: any[] = [
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { // red
      backgroundColor: 'rgba(255,0,0,0.3)',
      borderColor: 'red',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
  */

  public lineChartLegend = true;
  public lineChartType = 'line';

  @ViewChild(BaseChartDirective, { static: true }) chart!: BaseChartDirective;

  /**
   * TODO: Chart data end here.
   */

  getQuote() {
    if (typeof this.symbol === 'undefined' || !this.symbol) {
      return;
    }

    this.symbol = this.symbol.toUpperCase();

    this.channel$ = this.stockChartService.getQuote(this.symbol).subscribe({
      next: (response: any) => {
        console.log('stock-chart', 'getQuote', 'response', response);
        this.stock = response;
      },
      error: (error: HttpErrorResponse) => {
        console.error('stock-chart', 'getQuote', 'error', error);
        this.stock = null;
      },
      complete: () => {
        console.log('stock-chart', 'getQuote', 'complete');
      }
    });
  }

  ngOnInit(): void { }

  ngOnDestroy() {
    if (this.channel$) {
      this.channel$.unsubscribe();
    }
  }

  /**
   * TODO: Chart start here.
   */

  public randomize(): void {
    for (let i = 0; i < this.lineChartData.datasets.length; i++) {
      for (let j = 0; j < this.lineChartData[i].data.length; j++) {
        this.lineChartData[i].data[j] = this.generateNumber(i);
      }
    }
    this.chart.update();
  }

  private generateNumber(i: number) {
    return Math.floor((Math.random() * (i < 2 ? 100 : 1000)) + 1);
  }

  // events
  /*
  public chartClicked({ event, active }: { event: MouseEvent, active }): void {
    // console.log(event, active);
  }
  */

  /*
  public chartHovered({ event, active }: { event: MouseEvent, active }): void {
    // console.log(event, active);
  }
  */

  public hideOne() {
    const isHidden = this.chart.isDatasetHidden(1);
    this.chart.hideDataset(1, !isHidden);
  }

  public pushOne() {
    this.lineChartData.datasets.forEach((x, i) => {
      const num = this.generateNumber(i);
      const data: number[] = x.data as number[];
      data.push(num);
    });
    this.lineChartLabels.push(`Label ${this.lineChartLabels.length}`);
  }

  public changeColor() {
    // this.lineChartColors[2].borderColor = 'green';
    // this.lineChartColors[2].backgroundColor = 'rgba(0, 255, 0, 0.3)';
  }

  public changeLabel() {
    // this.lineChartLabels[2] = ['1st Line', '2nd Line'];
    // this.chart.update();
  }

  /** Pie Chart Starts Here */

  public pieChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    plugins: {
      title: {
        display: true,
        color: 'blue',
        text: 'Pie Chart'
      },
      legend: {
        display: true,
        position: 'top',
      },
      datalabels: {
        formatter: (value: any, context: Context) => {
          return value + ' MB';
        }
      },
      tooltip: {
        callbacks: {
          label: (tooltipItem: TooltipItem<keyof ChartTypeRegistry>) => {
            const index = tooltipItem.dataIndex;
            return tooltipItem.label + ' ' + formatNumber(Number(tooltipItem.dataset.data[index]), 'en-US', '1.0-1') + ' MBi';
          }
        }
      }
    },
  };

  public pieChartData: ChartConfiguration['data'] = {
    labels: ['Low', 'Middle', 'High'],
    datasets: [{
      data: [25, 40, 35],
      backgroundColor: ['rgba(0, 160, 0, 1)', 'rgba(240, 160, 0, 1)', 'rgba(220, 0, 0, 1)'],
      borderColor: ['rgba(250, 250, 250, 1)', 'rgba(250, 250, 250, 1)', 'rgba(250, 250, 250, 1)'],
      hoverBackgroundColor: ['rgba(0, 160, 0, 0.8)', 'rgba(240, 160, 0, 0.8)', 'rgba(220, 0, 0, 0.8)'],
      hoverBorderColor: ['rgba(0, 160, 0, 1)', 'rgba(240, 160, 0, 1)', 'rgba(220, 0, 0, 1)'],
    }],
  };

  public pieChartType: ChartType = 'pie';
}
