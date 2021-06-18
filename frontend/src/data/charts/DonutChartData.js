let generatedData

export const getDonutChartData = (themes) => {
  if (generatedData) {
    generatedData.datasets[0].backgroundColor = [themes.danger, themes.info, themes.primary]
  } else {
    generatedData = {
      labels: ['Interné', 'Externé'],
      datasets: [{
        label: 'Population (millions)',
        backgroundColor: [themes.danger, themes.info],
        data: [5, 8],
      }],
    }
  }

  return generatedData
}
