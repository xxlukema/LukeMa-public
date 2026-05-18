
create procedure Luke_Proc
   @ticker varchar(12),
   @swapNum int output,
   @swapId int output,
   @date smalldatetime output,
   @rate float output
as
begin
   select @swapNum = s.swapNum, @rate = d.rate, @swapId = d.swapId, @date = d.date
   from Swap s, SwapLeg l, RTC_DividendsBasketEventNtl b , DividendFX d , Instrument i 
   where s.fiId = l.parentId 
   and l.fiId = b.legId 
   and i.fiId = b.instrId 
   and i.ticker = @ticker
   and b.settleDate = d.date 
   and s.fiId = d.swapId 
   and i.fiId = d.instrId 
   order by b.tradeDate, s.swapNum
end


--drop procedure Luke_Proc

/*
use ETS_ESS_TEST
go

declare   @swapNum int
declare   @swapId int
declare   @date smalldatetime
declare   @rate float


exec Luke_Proc '.CSI300UT', @swapNum output, @swapId output, @date output, @rate output

select @swapNum, @swapId, @date, @rate
*/

