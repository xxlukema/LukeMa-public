# `RxJs` `Observable` Publish/Subscrbe

## 1. Service

    src\app\utils\rxjs\my-observable.service.ts

## 2. Publish

    src\app\my-observable\my-observable.component.ts

## 3. Subscribe

    src\app\nav\nav.component.ts

## Four Different Kind of Subjects

<https://stackoverflow.com/questions/43348463/what-is-the-difference-between-subject-and-behaviorsubject>

<https://github.com/piecioshka/rxjs-subject-vs-behavior-vs-replay-vs-async>

<table>
<thead>
<tr><th></th><th>Each next subscribers receive...</th></tr>
</thead>
<tbody>
<tr><tr><td>Subject</td><td>...only upcoming values</td></tr></tr>
<tr><td>BehaviorSubject</td><td>...one previous value and upcoming values</td></tr>
<tr><td>ReplaySubject</td><td>...all previous values and upcoming values</td></tr>
<tr><td>AsyncSubject</td><td>...the latest value when the stream will close</td></tr>
</tbody>
</table>
