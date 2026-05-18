

export const convertToDate = (epoch: number) => {
  const date = new Date(epoch * 1000);
  console.log(date.toISOString());
  return date;
};

export const timeFormatter = (time: number) => {
  const graphTime = new Date(time).toLocaleString([], {
    hour: '2-digit', minute: '2-digit'
  });

  return graphTime;
};
